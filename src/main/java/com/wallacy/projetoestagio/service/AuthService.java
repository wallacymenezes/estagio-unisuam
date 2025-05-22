package com.wallacy.projetoestagio.service;

import com.wallacy.projetoestagio.dto.LoginRequest;
import com.wallacy.projetoestagio.dto.UserDTO;
import com.wallacy.projetoestagio.mapper.UserMapper;
import com.wallacy.projetoestagio.model.PasswordRecovery;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.PasswordRecoveryRepository;
import com.wallacy.projetoestagio.repository.UserRepository;
import com.wallacy.projetoestagio.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {

    private final Long otpExpirationMinutes = 15L;

    private final UserRepository userRepository;
    private final PasswordRecoveryRepository recuperadorSenhaRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordRecoveryRepository recuperadorSenhaRepository,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.recuperadorSenhaRepository = recuperadorSenhaRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public Optional<UserDTO> authenticateUsers(LoginRequest loginRequest) {
        try {
            var credentials = new UsernamePasswordAuthenticationToken(loginRequest.user(), loginRequest.password());

            Authentication authentication = authenticationManager.authenticate(credentials);

            if (authentication.isAuthenticated()) {
                Optional<User> userOpt = userRepository.findByEmail(loginRequest.user());

                if (userOpt.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
                }

                User user = userOpt.get();

                UserDTO userDTO = UserMapper.toDTO(user);

                userDTO.setToken(generateToken(user.getEmail()));
                userDTO.setPassword("");  // limpa senha

                return Optional.of(userDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
            }
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas", e);
        }
    }

    // 1. Gera e envia OTP para recuperação de senha
    public void createRecoverToken(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        String otp = generateOtp();

        PasswordRecovery entity = new PasswordRecovery();
        entity.setEmail(email);
        entity.setOtp(otp);
        entity.setExpiration(Instant.now().plusSeconds(otpExpirationMinutes * 60L));
        recuperadorSenhaRepository.save(entity);

        String text = "Seu código de redefinição de senha: \n\n"
                + otp + ". Validade de " + otpExpirationMinutes + " minutos";

        emailService.sendEmail(email, "Recuperação de Senha", text);
    }

    // 2. Valida OTP e gera token JWT retornando UserDTO
    public Optional<UserDTO> validateOtpAndGenerateToken(String otp) {
        PasswordRecovery recoveryRequest = recuperadorSenhaRepository.findValidOtp(otp, Instant.now())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP inválido ou expirado"));

        Optional<User> userOpt = userRepository.findByEmail(recoveryRequest.getEmail());
        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        User user = userOpt.get();

        UserDTO userDTO = UserMapper.toDTO(user);
        userDTO.setToken(generateToken(recoveryRequest.getEmail()));
        userDTO.setPassword(""); // limpa senha

        return Optional.of(userDTO);
    }

    // 3. Altera senha validando token JWT
    @Transactional
    public void changePassword(UserDTO userDTO) {
        Optional<User> userOpt = userRepository.findByEmail(userDTO.getEmail());

        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        User user = userOpt.get();

        if (userDTO.getToken() == null || userDTO.getToken().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido para alteração de senha");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    private String generateOtp() {
        return String.format("%06d", (int)(Math.random() * 1000000)); // OTP 6 dígitos
    }

    private String generateToken(String usuario) {
        return "Bearer " + jwtService.generateToken(usuario);
    }
}
