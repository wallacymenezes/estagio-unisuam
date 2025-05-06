package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.InvestmentDTO;
import com.wallacy.projetoestagio.mapper.InvestmentMapper;
import com.wallacy.projetoestagio.model.Investment;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.InvestmentRepository;
import com.wallacy.projetoestagio.util.TokenUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/investments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvestmentController {

    private final InvestmentRepository investmentRepository;

    public InvestmentController(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<InvestmentDTO>> getAll(JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            List<InvestmentDTO> investments = user.getInvestments().stream()
                    .map(InvestmentMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(investments);
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentDTO> getById(@PathVariable Long id, JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            Optional<Investment> investment = investmentRepository.findById(id);
            if (investment.isPresent() && investment.get().getUser().equals(user)) {
                return ResponseEntity.ok(InvestmentMapper.toDTO(investment.get()));
            } else {
                return ResponseEntity.status(403).<InvestmentDTO>body(null);
            }
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @PostMapping
    public ResponseEntity<InvestmentDTO> create(@Valid @RequestBody InvestmentDTO dto, JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            Investment investment = InvestmentMapper.toEntity(dto, user);
            Investment saved = investmentRepository.save(investment);
            return ResponseEntity.status(201).body(InvestmentMapper.toDTO(saved));
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @PutMapping
    public ResponseEntity<InvestmentDTO> update(@Valid @RequestBody InvestmentDTO dto, JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            Optional<Investment> optionalInvestment = investmentRepository.findById(dto.getId());
            if (optionalInvestment.isPresent() && optionalInvestment.get().getUser().equals(user)) {
                Investment investment = optionalInvestment.get();
                InvestmentMapper.updateEntityFromDTO(investment, dto);
                investmentRepository.save(investment);
                return ResponseEntity.ok(InvestmentMapper.toDTO(investment));
            } else {
                return ResponseEntity.status(403).<InvestmentDTO>body(null);
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
        Optional<Investment> optionalInvestment = investmentRepository.findById(id);

        if (optionalInvestment.isPresent() && optionalInvestment.get().getUser().equals(user)) {
            investmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }
}
