package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.ExpenseDTO;
import com.wallacy.projetoestagio.mapper.ExpenseMapper;
import com.wallacy.projetoestagio.model.Expense;
import com.wallacy.projetoestagio.repository.ExpenseRepository;
import com.wallacy.projetoestagio.util.TokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAll(JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            List<ExpenseDTO> expenses = user.getExpenses().stream()
                    .map(ExpenseMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(expenses);
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getById(@PathVariable Long id, JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            Optional<Expense> expense = expenseRepository.findById(id);
            if (expense.isPresent() && expense.get().getUser().equals(user)) {
                return ResponseEntity.ok(ExpenseMapper.toDTO(expense.get()));
            } else {
                return ResponseEntity.status(403).<ExpenseDTO>body(null);
            }
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }
}