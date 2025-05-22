package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.EarningDTO;
import com.wallacy.projetoestagio.mapper.EarningMapper;
import com.wallacy.projetoestagio.model.Earning;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.EarningRepository;
import com.wallacy.projetoestagio.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/earnings")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EarningController {

    private final EarningRepository earningRepository;
    private final UserRepository userRepository;

    public EarningController(EarningRepository earningRepository, UserRepository userRepository) {
        this.earningRepository = earningRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<EarningDTO>> getAll() {
        List<Earning> earnings = earningRepository.findAll();
        List<EarningDTO> dtos = earnings.stream()
                .map(EarningMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EarningDTO> getById(@PathVariable Long id) {
        Optional<Earning> earningOpt = earningRepository.findById(id);
        return earningOpt
                .map(earning -> ResponseEntity.ok(EarningMapper.toDTO(earning)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EarningDTO> create(@Valid @RequestBody EarningDTO dto) {
        Optional<User> userOpt = userRepository.findByUserId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Earning earning = EarningMapper.toEntity(dto, userOpt.get());
        Earning saved = earningRepository.save(earning);
        return ResponseEntity.status(201).body(EarningMapper.toDTO(saved));
    }

    @PutMapping
    public ResponseEntity<EarningDTO> update(@Valid @RequestBody EarningDTO dto) {
        Optional<Earning> earningOpt = earningRepository.findById(dto.getId());
        if (earningOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> userOpt = userRepository.findByUserId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Earning earning = earningOpt.get();
        EarningMapper.updateEntityFromDTO(earning, dto);
        earning.setUser(userOpt.get());
        earningRepository.save(earning);

        return ResponseEntity.ok(EarningMapper.toDTO(earning));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (earningRepository.existsById(id)) {
            earningRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EarningDTO>> getByUserId(@PathVariable String userId) {
        Optional<User> userOpt = userRepository.findByUserId(java.util.UUID.fromString(userId));
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Earning> earnings = earningRepository.findByUserUserId(userOpt.get().getUserId());
        List<EarningDTO> dtoList = earnings.stream()
                .map(EarningMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
}

