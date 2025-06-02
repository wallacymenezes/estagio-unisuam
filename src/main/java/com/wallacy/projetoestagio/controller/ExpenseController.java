package com.wallacy.projetoestagio.controller;

import com.wallacy.projetoestagio.dto.ExpenseDTO;
import com.wallacy.projetoestagio.dto.ExpenseStatusUpdateDTO; // Importar o novo DTO
import com.wallacy.projetoestagio.mapper.ExpenseMapper;
import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.Expense;
import com.wallacy.projetoestagio.enums.ExpenseStatus; // Importar o ExpenseStatus
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.CategoryRepository;
import com.wallacy.projetoestagio.repository.ExpenseRepository;
import com.wallacy.projetoestagio.repository.UserRepository;
import jakarta.validation.Valid; // Importar para usar @Valid no DTO de atualização
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserRepository userRepository;

    public ExpenseController(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAll() {
        List<Expense> expenses = expenseRepository.findAll();
        List<ExpenseDTO> dtos = expenses.stream()
                .map(ExpenseMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getById(@PathVariable Long id) {
        Optional<Expense> expenseOpt = expenseRepository.findById(id);
        return expenseOpt
                .map(expense -> ResponseEntity.ok(ExpenseMapper.toDTO(expense)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ExpenseDTO expenseDTO) { // Adicionado @Valid
        Optional<User> userOpt = userRepository.findByUserId(expenseDTO.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        Optional<Category> categoryOpt = categoryRepository.findById(expenseDTO.getCategory());
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Categoria não encontrada");
        }

        // O status será definido no toEntity, com um padrão se não vier no DTO
        Expense expense = ExpenseMapper.toEntity(expenseDTO, categoryOpt.get(), userOpt.get());
        Expense savedExpense = expenseRepository.save(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(ExpenseMapper.toDTO(savedExpense));
    }


    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody ExpenseDTO expenseDTO) { // Adicionado @Valid
        Optional<Expense> existingExpenseOpt = expenseRepository.findById(expenseDTO.getId());
        if (existingExpenseOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> userOpt = userRepository.findByUserId(expenseDTO.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        Optional<Category> categoryOpt = categoryRepository.findById(expenseDTO.getCategory());
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Categoria não encontrada");
        }

        Expense expense = existingExpenseOpt.get();
        // O método updateEntityFromDTO agora lida com a atualização do status se ele estiver no DTO
        ExpenseMapper.updateEntityFromDTO(expense, expenseDTO, categoryOpt.get());
        expense.setUser(userOpt.get()); // Garante que o usuário associado permanece correto
        Expense savedExpense = expenseRepository.save(expense);
        return ResponseEntity.ok(ExpenseMapper.toDTO(savedExpense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseDTO>> getByUserId(@PathVariable String userId) {
        Optional<User> userOpt = userRepository.findByUserId(java.util.UUID.fromString(userId));
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Expense> expenses = expenseRepository.findByUserUserId(userOpt.get().getUserId());
        List<ExpenseDTO> dtoList = expenses.stream()
                .map(ExpenseMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ExpenseDTO>> getByCategoryId(@PathVariable Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Expense> expenses = expenseRepository.findByCategory(categoryOpt.get());
        List<ExpenseDTO> dtoList = expenses.stream()
                .map(ExpenseMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // NOVO ENDPOINT: Atualizar apenas o status de uma despesa
    @PatchMapping("/{id}/status")
    public ResponseEntity<ExpenseDTO> updateExpenseStatus(@PathVariable Long id, @Valid @RequestBody ExpenseStatusUpdateDTO statusUpdateDTO) {
        // Verifica se o ID do path corresponde ao ID do DTO
        if (!id.equals(statusUpdateDTO.getId())) {
            return ResponseEntity.badRequest().body(null); // Ou retornar um erro mais específico
        }

        Optional<Expense> expenseOpt = expenseRepository.findById(id);
        if (expenseOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Expense expense = expenseOpt.get();
        expense.setStatus(statusUpdateDTO.getStatus()); // Atualiza apenas o status
        Expense updatedExpense = expenseRepository.save(expense);
        return ResponseEntity.ok(ExpenseMapper.toDTO(updatedExpense));
    }

    // NOVO ENDPOINT: Listar despesas por status e usuário
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByUserIdAndStatus(@PathVariable String userId, @PathVariable String status) {
        Optional<User> userOpt = userRepository.findByUserId(java.util.UUID.fromString(userId));
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ExpenseStatus expenseStatus;
        try {
            expenseStatus = ExpenseStatus.valueOf(status.toUpperCase()); // Converte a string do path para o Enum
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Status inválido
        }

        List<Expense> expenses = expenseRepository.findByUserUserIdAndStatus(userOpt.get().getUserId(), expenseStatus);
        List<ExpenseDTO> dtoList = expenses.stream()
                .map(ExpenseMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
}