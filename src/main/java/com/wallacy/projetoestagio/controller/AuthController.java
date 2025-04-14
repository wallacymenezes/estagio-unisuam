package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.*;
import com.wallacy.projetoestagio.service.AuthService;
import com.wallacy.projetoestagio.service.RecoveryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RecoveryService recoveryService;

    public AuthController(AuthService authService, RecoveryService recoveryService) {
        this.authService = authService;
        this.recoveryService = recoveryService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    // Endpoint para solicitar o envio do otp de recuperação
    @PostMapping("/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailRequest emailRequest) {
        recoveryService.createRecoverToken(emailRequest.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<Optional<LoginResponse>> validateOtp(@Valid @RequestBody CodeConfirm codeConfirm) {
        Optional<LoginResponse> loginResponse = recoveryService.validateOtpAndGenerateToken(codeConfirm);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody String passwordChanged, JwtAuthenticationToken accessToken) {
        recoveryService.changePassword(passwordChanged, accessToken);
        return ResponseEntity.noContent().build();
    }
}
