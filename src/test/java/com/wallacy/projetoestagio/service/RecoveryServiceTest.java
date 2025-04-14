package com.wallacy.projetoestagio.service;

import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.PasswordRecoveryRepository;
import com.wallacy.projetoestagio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecoveryServiceTest {

    @InjectMocks
    private RecoveryService recoveryService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordRecoveryRepository passwordRecoveryRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtEncoder jwtEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void changePassword_shouldUpdatePassword_whenValidData() {
        // Arrange
        String newPassword = "novasenha123";
        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setUserId(userId);
        user.setEmail("teste@email.com");
        user.setPassword("senhaAntiga");

        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(userId.toString());
        when(token.isAuthenticated()).thenReturn(true);

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("senhaCriptografada");

        // Act
        recoveryService.changePassword(newPassword, token);

        // Assert
        verify(userRepository).save(user);
        verify(passwordRecoveryRepository).deleteByEmail(user.getEmail());
        assertEquals("senhaCriptografada", user.getPassword());
    }

    @Test
    void changePassword_shouldThrow_whenTokenIsInvalid() {
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.isAuthenticated()).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> recoveryService.changePassword("senhaValida123", token));

        assertEquals("400 BAD_REQUEST \"Token inválido\"", ex.getMessage());
    }

    @Test
    void changePassword_shouldThrow_whenPasswordIsEmpty() {
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.isAuthenticated()).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> recoveryService.changePassword("", token));

        assertEquals("400 BAD_REQUEST \"Senha não pode ser vazia\"", ex.getMessage());
    }

    @Test
    void changePassword_shouldThrow_whenPasswordTooShort() {
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.isAuthenticated()).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> recoveryService.changePassword("123", token));

        assertEquals("400 BAD_REQUEST \"Senha deve ter no mínimo 8 caracteres\"", ex.getMessage());
    }

    @Test
    void changePassword_shouldThrow_whenUserNotFound() {
        UUID userId = UUID.randomUUID();
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.isAuthenticated()).thenReturn(true);
        when(token.getName()).thenReturn(userId.toString());

        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> recoveryService.changePassword("senhaValida123", token));

        assertEquals("404 NOT_FOUND \"Usuário não encontrado\"", ex.getMessage());
    }
}
