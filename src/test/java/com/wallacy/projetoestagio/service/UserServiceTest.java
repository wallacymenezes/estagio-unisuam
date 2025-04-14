package com.wallacy.projetoestagio.service;

import com.wallacy.projetoestagio.dto.UserDTO;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.UserRepository;
import com.wallacy.projetoestagio.mapper.UserMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --------------------------
    // ✅ TESTE: registerUser
    // --------------------------

    @Test
    void registerUser_shouldSaveNewUser_whenEmailIsUnique() {
        UserDTO dto = new UserDTO(UUID.randomUUID(), "Teste", "teste@email.com", "senha123", null);
        dto.setEarnings(new ArrayList<>());
        dto.setExpenses(new ArrayList<>());
        User userEntity = UserMapper.toEntity(dto);
        userEntity.setPassword("senhaCriptografada");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("senhaCriptografada");
        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        Optional<UserDTO> result = userService.registerUser(dto);

        assertTrue(result.isPresent());
        assertEquals(dto.getEmail(), result.get().getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_shouldThrow_whenEmailAlreadyExists() {
        UserDTO dto = new UserDTO(UUID.randomUUID(), "Teste", "ja@existe.com", "senha123", null);

        when(userRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(new User()));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.registerUser(dto));

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ex.getStatusCode());
    }

    // --------------------------
    // ✅ TESTE: updateUser
    // --------------------------

    @Test
    void updateUser_shouldUpdateUser_whenUserIsValidAndLoggedIn() {
        UUID userId = UUID.randomUUID();
        UserDTO dto = new UserDTO(userId, "Atualizado", "novo@email.com", "novaSenha123", "foto.png");

        User existing = new User();
        existing.setUserId(userId);
        existing.setEmail("velho@email.com");
        existing.setName("Antigo");

        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(userId.toString());

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(existing));
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("senhaNovaHash");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        Optional<UserDTO> result = userService.updateUser(dto, token);

        assertTrue(result.isPresent());
        assertEquals(dto.getEmail(), result.get().getEmail());
        assertEquals(dto.getName(), result.get().getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_shouldThrow_whenLoggedUserTriesToEditAnotherUser() {
        UserDTO dto = new UserDTO(UUID.randomUUID(), "Usuário", "email@email.com", null, null);
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(UUID.randomUUID().toString()); // id diferente

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.updateUser(dto, token));

        assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
    }

    @Test
    void updateUser_shouldThrow_whenUserNotFound() {
        UUID userId = UUID.randomUUID();
        UserDTO dto = new UserDTO(userId, "Usuário", "email@email.com", null, null);
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(userId.toString());

        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.updateUser(dto, token));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void updateUser_shouldThrow_whenEmailAlreadyUsedByAnotherUser() {
        UUID userId = UUID.randomUUID();
        UserDTO dto = new UserDTO(userId, "Novo Nome", "email@usado.com", null, null);
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(userId.toString());

        User existing = new User();
        existing.setUserId(userId);

        User outroUsuario = new User();
        outroUsuario.setUserId(UUID.randomUUID()); // outro id

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(existing));
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(outroUsuario));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.updateUser(dto, token));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }
}
