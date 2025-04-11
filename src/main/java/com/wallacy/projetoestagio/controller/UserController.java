package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.UserDTO;
import com.wallacy.projetoestagio.mapper.UserMapper;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.UserRepository;
import com.wallacy.projetoestagio.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable UUID id) {
        return userRepository.findByUserId(id)
                .map(user -> ResponseEntity.ok(UserMapper.toDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> postUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping
    public ResponseEntity<UserDTO> putUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
