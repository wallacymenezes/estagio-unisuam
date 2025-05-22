package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.UserDTO;
import com.wallacy.projetoestagio.mapper.UserMapper;
import com.wallacy.projetoestagio.repository.UserRepository;
import com.wallacy.projetoestagio.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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
