package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.InvestmentDTO;
import com.wallacy.projetoestagio.mapper.InvestmentMapper;
import com.wallacy.projetoestagio.model.Investment;
import com.wallacy.projetoestagio.model.Objective;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.InvestmentRepository;
import com.wallacy.projetoestagio.repository.ObjectiveRepository;
import com.wallacy.projetoestagio.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/investments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvestmentController {

    private final InvestmentRepository investmentRepository;
    private final ObjectiveRepository objectiveRepository;
    private final UserRepository userRepository;

    public InvestmentController(InvestmentRepository investmentRepository, ObjectiveRepository objectiveRepository, UserRepository userRepository) {
        this.investmentRepository = investmentRepository;
        this.objectiveRepository = objectiveRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<InvestmentDTO>> getAll() {
        List<Investment> investments = investmentRepository.findAll();
        List<InvestmentDTO> dtos = investments.stream()
                .map(InvestmentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentDTO> getById(@PathVariable Long id) {
        Optional<Investment> investmentOpt = investmentRepository.findById(id);
        return investmentOpt
                .map(investment -> ResponseEntity.ok(InvestmentMapper.toDTO(investment)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody InvestmentDTO dto) {
        Optional<User> userOpt = userRepository.findByUserId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }

        Objective objective = null;
        if (dto.getObjectiveId() != null) {
            objective = objectiveRepository.findById(dto.getObjectiveId()).orElse(null);
        }

        Investment investment = InvestmentMapper.toEntity(dto, userOpt.get(), objective);
        Investment saved = investmentRepository.save(investment);
        return ResponseEntity.status(201).body(InvestmentMapper.toDTO(saved));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody InvestmentDTO dto) {
        Optional<Investment> investmentOpt = investmentRepository.findById(dto.getId());
        if (investmentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> userOpt = userRepository.findByUserId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }

        Objective objective = null;
        if (dto.getObjectiveId() != null) {
            objective = objectiveRepository.findById(dto.getObjectiveId()).orElse(null);
        }

        Investment investment = investmentOpt.get();
        InvestmentMapper.updateEntityFromDTO(investment, dto, objective);
        investment.setUser(userOpt.get());
        investmentRepository.save(investment);

        return ResponseEntity.ok(InvestmentMapper.toDTO(investment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!investmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        investmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Novo endpoint: investimentos de um usuário pelo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvestmentDTO>> getByUserId(@PathVariable String userId) {
        Optional<User> userOpt = userRepository.findByUserId(java.util.UUID.fromString(userId));
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Investment> investments = investmentRepository.findByUserUserId(userOpt.get().getUserId());
        List<InvestmentDTO> dtos = investments.stream()
                .map(InvestmentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Novo endpoint: investimentos por objectiveId
    @GetMapping("/objective/{objectiveId}")
    public ResponseEntity<List<InvestmentDTO>> getByObjectiveId(@PathVariable Long objectiveId) {
        List<Investment> investments = investmentRepository.findByObjectiveId(objectiveId);
        List<InvestmentDTO> dtos = investments.stream()
                .map(InvestmentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}

