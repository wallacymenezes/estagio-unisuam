package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.*;
import com.wallacy.projetoestagio.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Optional<UserDTO>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUsers(loginRequest));
    }

    // Endpoint para solicitar o envio do otp de recuperação
    @PostMapping("/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailRequest emailRequest) {
        authService.createRecoverToken(emailRequest.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<Optional<UserDTO>> validateOtp(@Valid @RequestBody String otp) {
        Optional<UserDTO> userDTO = authService.validateOtpAndGenerateToken(otp);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody UserDTO userDTO) {
        authService.changePassword(userDTO);
        return ResponseEntity.noContent().build();
    }
}
