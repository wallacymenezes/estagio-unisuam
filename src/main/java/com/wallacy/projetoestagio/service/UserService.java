package com.wallacy.projetoestagio.service;

import com.wallacy.projetoestagio.dto.UserDTO;
import com.wallacy.projetoestagio.mapper.UserMapper;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UserDTO> registerUser(UserDTO userDTO) {
        // Verifica se email já está cadastrado
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return Optional.empty();  // retorna vazio se usuário já existe (ou pode lançar exception)
        }

        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return Optional.of(UserMapper.toDTO(savedUser));
    }

    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        // Verifica se usuário com id existe
        Optional<User> existingUserOpt = userRepository.findByUserId(userDTO.getUserId());

        if (existingUserOpt.isEmpty()) {
            return Optional.empty(); // usuário não existe
        }

        // Verifica se email não está em uso por outro usuário
        Optional<User> userWithEmail = userRepository.findByEmail(userDTO.getEmail());
        if (userWithEmail.isPresent() && !Objects.equals(userWithEmail.get().getUserId(), userDTO.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já está sendo usado por outro usuário.");
        }

        User user = existingUserOpt.get();

        // Atualiza campos
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
