package com.wallacy.projetoestagio.service;

import com.wallacy.projetoestagio.dto.CodeConfirm;
import com.wallacy.projetoestagio.dto.LoginResponse;
import com.wallacy.projetoestagio.model.PasswordRecovery;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.PasswordRecoveryRepository;
import com.wallacy.projetoestagio.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.wallacy.projetoestagio.dto.EmailRequest.email;

@Service
public class RecoveryService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final PasswordRecoveryRepository passwordRecoveryRepository;

    public RecoveryService(EmailService emailService,
                           UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtEncoder jwtEncoder,
                           PasswordRecoveryRepository passwordRecoveryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.emailService = emailService;
        this.passwordRecoveryRepository = passwordRecoveryRepository;
    }

    public void createRecoverToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        String otp = generateOtp();

        PasswordRecovery recovery = new PasswordRecovery();
        recovery.setEmail(email);
        recovery.setOtp(otp);
        long otpExpirationMinutes = 15L;
        recovery.setExpiration(Instant.now().plusSeconds(otpExpirationMinutes * 60));
        passwordRecoveryRepository.save(recovery);

        String text = "Seu código de redefinição de senha: \n\n" + otp +
                "\nValidade de " + otpExpirationMinutes + " minutos";

        emailService.sendEmail(email, "Recuperação de Senha", text);
    }

    public Optional<LoginResponse> validateOtpAndGenerateToken(CodeConfirm codeConfirm) {
        // Verifica se o OTP é válido e não expirou
        passwordRecoveryRepository.findValidOtp(codeConfirm.getOtp(), Instant.now())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP"));

        // Busca o usuário pelo e-mail informado no CodeConfirm
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            Instant now = Instant.now();
            long expiresIn = 300L;

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .subject(user.get().getUserId().toString())
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresIn))
                    .build();

            String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return Optional.of(new LoginResponse(token, expiresIn));
        }

        // Usuário não encontrado — retorno vazio
        return Optional.empty();
    }

    public void changePassword(String newPassword, JwtAuthenticationToken accessToken) {

        if (!accessToken.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha não pode ser vazia");
        }
        if (newPassword.length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha deve ter no mínimo 8 caracteres");
        }

        User user = userRepository.findByUserId(UUID.fromString(accessToken.getName()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nova senha não pode ser igual à anterior");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        // Remove o OTP após a troca de senha
        passwordRecoveryRepository.deleteByEmail(user.getEmail());

        userRepository.save(user);
    }

    private String generateOtp() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}
