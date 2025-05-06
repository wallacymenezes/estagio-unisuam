package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.ObjectiveDTO;
import com.wallacy.projetoestagio.mapper.ObjectiveMapper;
import com.wallacy.projetoestagio.model.Objective;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.ObjectiveRepository;
import com.wallacy.projetoestagio.util.TokenUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/objectives")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ObjectiveController {

    private final ObjectiveRepository objectiveRepository;

    public ObjectiveController(ObjectiveRepository objectiveRepository) {
        this.objectiveRepository = objectiveRepository;
    }

    @GetMapping
    public ResponseEntity<List<ObjectiveDTO>> getAll(JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            List<ObjectiveDTO> objectives = user.getObjectives().stream()
                    .map(ObjectiveMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(objectives);
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectiveDTO> getById(@PathVariable Long id, JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            Optional<Objective> objective = objectiveRepository.findById(id);
            if (objective.isPresent() && objective.get().getUser().equals(user)) {
                return ResponseEntity.ok(ObjectiveMapper.toDTO(objective.get()));
            } else {
                return ResponseEntity.status(403).<ObjectiveDTO>body(null);
            }
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @PostMapping
    public ResponseEntity<ObjectiveDTO> create(@Valid @RequestBody ObjectiveDTO dto, JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            Objective objective = ObjectiveMapper.toEntity(dto, user);
            Objective saved = objectiveRepository.save(objective);
            return ResponseEntity.status(201).body(ObjectiveMapper.toDTO(saved));
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @PutMapping
    public ResponseEntity<ObjectiveDTO> update(@Valid @RequestBody ObjectiveDTO dto, JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            Optional<Objective> optionalObjective = objectiveRepository.findById(dto.getId());
            if (optionalObjective.isPresent() && optionalObjective.get().getUser().equals(user)) {
                Objective objective = optionalObjective.get();
                ObjectiveMapper.updateEntityFromDTO(objective, dto);
                objectiveRepository.save(objective);
                return ResponseEntity.ok(ObjectiveMapper.toDTO(objective));
            } else {
                return ResponseEntity.status(403).<ObjectiveDTO>body(null);
            }
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, JwtAuthenticationToken token) {
        Optional<User> userOptional = TokenUtils.getUserFromToken(token);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        User user = userOptional.get();
        Optional<Objective> optionalObjective = objectiveRepository.findById(id);

        if (optionalObjective.isPresent() && optionalObjective.get().getUser().equals(user)) {
            objectiveRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }
}
