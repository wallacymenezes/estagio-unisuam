package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.ObjectiveDTO;
import com.wallacy.projetoestagio.mapper.ObjectiveMapper;
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
import java.util.UUID;

@RestController
@RequestMapping("/objectives")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ObjectiveController {

    private final ObjectiveRepository objectiveRepository;
    private final InvestmentRepository investmentRepository;
    private final UserRepository userRepository;

    public ObjectiveController(ObjectiveRepository objectiveRepository, InvestmentRepository investmentRepository, UserRepository userRepository) {
        this.objectiveRepository = objectiveRepository;
        this.investmentRepository = investmentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<ObjectiveDTO>> getAll() {
        List<Objective> objectives = objectiveRepository.findAll();
        List<ObjectiveDTO> dtos = objectives.stream()
                .map(ObjectiveMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectiveDTO> getById(@PathVariable Long id) {
        Optional<Objective> optionalObjective = objectiveRepository.findById(id);
        return optionalObjective
                .map(objective -> ResponseEntity.ok(ObjectiveMapper.toDTO(objective)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ObjectiveDTO dto) {
        Optional<User> userOpt = userRepository.findByUserId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        Objective objective = ObjectiveMapper.toEntity(dto, userOpt.get());
        Objective saved = objectiveRepository.save(objective);
        return ResponseEntity.status(201).body(ObjectiveMapper.toDTO(saved));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody ObjectiveDTO dto) {
        Optional<Objective> optionalObjective = objectiveRepository.findById(dto.getId());
        if (optionalObjective.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> userOpt = userRepository.findByUserId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        Objective objective = optionalObjective.get();
        ObjectiveMapper.updateEntityFromDTO(objective, dto);
        objective.setUser(userOpt.get());
        objectiveRepository.save(objective);
        return ResponseEntity.ok(ObjectiveMapper.toDTO(objective));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // 1. Verifica se o objetivo que queremos apagar realmente existe
        if (!objectiveRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // 2. Desassocia todos os investimentos em uma única e eficiente operação
        investmentRepository.disassociateInvestmentsFromObjective(id);

        // 3. Apaga o objetivo com segurança, pois não tem mais "filhos"
        objectiveRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // Novo endpoint para retornar todos os objetivos de um usuário
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ObjectiveDTO>> getByUserId(@PathVariable String userId) {
        Optional<User> userOpt = userRepository.findByUserId(UUID.fromString(userId));
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Objective> objectives = objectiveRepository.findByUserUserId(userOpt.get().getUserId());
        List<ObjectiveDTO> dtos = objectives.stream()
                .map(ObjectiveMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
