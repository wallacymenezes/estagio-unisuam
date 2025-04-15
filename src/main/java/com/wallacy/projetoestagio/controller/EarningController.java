package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.EarningDTO;
import com.wallacy.projetoestagio.mapper.EarningMapper;
import com.wallacy.projetoestagio.model.Earning;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.EarningRepository;
import com.wallacy.projetoestagio.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/earnings")
public class EarningController {

    private final EarningRepository earningRepository;
    private final UserRepository userRepository;

    public EarningController(EarningRepository earningRepository, UserRepository userRepository) {
        this.earningRepository = earningRepository;
        this.userRepository = userRepository;
    }

    private Optional<User> getUserFromToken(JwtAuthenticationToken token) {
        String email = token.getToken().getSubject();
        return userRepository.findByEmail(email);
    }

    @GetMapping
    public ResponseEntity<List<EarningDTO>> getAll(JwtAuthenticationToken token) {
        return getUserFromToken(token).map(user -> {
            List<EarningDTO> earnings = user.getEarnings().stream()
                    .map(EarningMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(earnings);
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EarningDTO> getById(@PathVariable Long id, JwtAuthenticationToken token) {
        return getUserFromToken(token).map(user -> {
            Optional<Earning> earning = earningRepository.findById(id);
            if (earning.isPresent() && earning.get().getUser().equals(user)) {
                return ResponseEntity.ok(EarningMapper.toDTO(earning.get()));
            } else {
                return ResponseEntity.status(403).<EarningDTO>body(null);
            }
        }).orElseGet(() -> ResponseEntity.status(401).<EarningDTO>body(null));
    }

    @PostMapping
    public ResponseEntity<EarningDTO> create(@Valid @RequestBody EarningDTO dto, JwtAuthenticationToken token) {
        return getUserFromToken(token).map(user -> {
            Earning earning = EarningMapper.toEntity(dto, user);
            Earning saved = earningRepository.save(earning);
            return ResponseEntity.status(201).body(EarningMapper.toDTO(saved));
        }).orElseGet(() -> ResponseEntity.status(401).<EarningDTO>body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EarningDTO> update(@PathVariable Long id, @Valid @RequestBody EarningDTO dto, JwtAuthenticationToken token) {
        return getUserFromToken(token).map(user -> {
            Optional<Earning> optionalEarning = earningRepository.findById(id);
            if (optionalEarning.isPresent() && optionalEarning.get().getUser().equals(user)) {
                Earning earning = optionalEarning.get();
                EarningMapper.updateEntityFromDTO(earning, dto);
                earningRepository.save(earning);
                return ResponseEntity.ok(EarningMapper.toDTO(earning));
            } else {
                return ResponseEntity.status(403).<EarningDTO>body(null);
            }
        }).orElseGet(() -> ResponseEntity.status(401).<EarningDTO>body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, JwtAuthenticationToken token) {
        Optional<User> userOptional = getUserFromToken(token);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        User user = userOptional.get();
        Optional<Earning> optionalEarning = earningRepository.findById(id);

        if (optionalEarning.isPresent() && optionalEarning.get().getUser().equals(user)) {
            earningRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }
}