package com.wallacy.projetoestagio.service;

import com.wallacy.projetoestagio.dto.UserDTO;
import com.wallacy.projetoestagio.mapper.UserMapper;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Optional<UserDTO> registerUser(UserDTO userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Usuário já existe!");
        }

        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(user);

        return Optional.of(UserMapper.toDTO(savedUser));
    }

    @Transactional
    public Optional<UserDTO> updateUser(UserDTO userDTO, JwtAuthenticationToken accessToken) {
        UUID loggedUserId = UUID.fromString(accessToken.getName());

        // Verifica se o usuário logado é o mesmo que está tentando ser editado
        if (!loggedUserId.equals(userDTO.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar este usuário");
        }

        Optional<User> existing = userRepository.findByUserId(userDTO.getUserId());

        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        // Verifica se o email está sendo usado por outro usuário
        Optional<User> userWithEmail = userRepository.findByEmail(userDTO.getEmail());
        if (userWithEmail.isPresent() && !userWithEmail.get().getUserId().equals(userDTO.getUserId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já está sendo usado por outro usuário.");
        }

        // Atualiza os campos
        User user = existing.get();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoto(userDTO.getPhoto());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);

        return Optional.of(UserMapper.toDTO(updatedUser));
    }

}
