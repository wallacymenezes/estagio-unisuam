package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.LoginRequest;
import com.wallacy.projetoestagio.dto.LoginResponse;
import com.wallacy.projetoestagio.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    // Endpoint para solicitar o envio do otp de recuperação
    /*
    @PostMapping("/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailRequest emailRequest) {
        authService.createRecoverToken(emailRequest.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<Optional<UsuarioLogin>> validateOtp(@Valid @RequestBody ConfirmaCodigo confirmaCodigo) {
        Optional<UsuarioLogin> usuarioLogin = authService.validateOtpAndGenerateToken(confirmaCodigo.getOtp(), confirmaCodigo.getEmail());
        return ResponseEntity.ok(usuarioLogin);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody UsuarioLogin usuarioLogin) {
        authService.changePassword(usuarioLogin);
        return ResponseEntity.noContent().build();
    }
    */

}
