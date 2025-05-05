package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.ExpenseDTO;
import com.wallacy.projetoestagio.mapper.ExpenseMapper;
import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.Expense;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.CategoryRepository;
import com.wallacy.projetoestagio.repository.ExpenseRepository;
import com.wallacy.projetoestagio.util.TokenUtils;
import org.springframework.http.HttpStatus;
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
    private final CategoryRepository categoryRepository;

    public ExpenseController(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ExpenseDTO expenseDTO, JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            Optional<Category> categoryOpt = categoryRepository.findByName(expenseDTO.getCategory());
            if (categoryOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Expense expense = ExpenseMapper.toEntity(expenseDTO, categoryOpt.get());
            expense.setUser(user);
            Expense savedExpense = expenseRepository.save(expense);
            return ResponseEntity.status(HttpStatus.CREATED).body(ExpenseMapper.toDTO(savedExpense));
        }).orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ExpenseDTO expenseDTO, JwtAuthenticationToken token) {
        return TokenUtils.getUserFromToken(token).map(user -> {
            Optional<Expense> existingExpense = expenseRepository.findById(expenseDTO.getId());
            Optional<Category> categoryOpt = categoryRepository.findByName(expenseDTO.getCategory());
            if (categoryOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (existingExpense.isPresent() && existingExpense.get().getUser().equals(user)) {
                Expense updatedExpense = ExpenseMapper.updateEntityFromDTO(existingExpense.get(), expenseDTO, categoryOpt.get());
                Expense savedExpense = expenseRepository.save(updatedExpense);
                return ResponseEntity.ok(ExpenseMapper.toDTO(savedExpense));
            } else {
                return ResponseEntity.status(403).<ExpenseDTO>body(null);
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
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isPresent() && optionalExpense.get().getUser().equals(user)) {
            expenseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

}
