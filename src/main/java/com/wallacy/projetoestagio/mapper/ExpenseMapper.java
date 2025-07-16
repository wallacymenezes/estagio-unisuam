package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.ExpenseDTO;
import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.Expense;
import com.wallacy.projetoestagio.enums.ExpenseStatus;
import com.wallacy.projetoestagio.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ExpenseMapper {

    /**
     * Converte uma entidade Expense para um ExpenseDTO.
     * Inclui os novos campos vencimento e last_update.
     */
    public static ExpenseDTO toDTO(Expense expense) {
        if (expense == null) {
            return null;
        }

        // Supondo que o ExpenseDTO foi atualizado para incluir os novos campos
        return new ExpenseDTO(
                expense.getId(),
                expense.getName(),
                expense.getDescription(),
                expense.getValue(),
                expense.getVencimento(), // Novo campo
                expense.getStatus(),
                expense.getCategory() != null ? expense.getCategory().getId() : null,
                expense.getUser() != null ? expense.getUser().getUserId() : null,
                expense.getCreation_date(),
                expense.getLast_update() // Novo campo
        );
    }

    /**
     * Converte um ExpenseDTO para a entidade Expense.
     * Ideal para criar uma nova entidade a partir de dados da API.
     */
    public static Expense toEntity(ExpenseDTO dto, Category category, User user) {
        if (dto == null) {
            return null;
        }

        Expense expense = new Expense();
        expense.setId(dto.getId()); // Será null para uma nova entidade
        expense.setName(dto.getName());
        expense.setDescription(dto.getDescription());
        expense.setValue(dto.getValue());
        expense.setVencimento(dto.getVencimento()); // Define a data de vencimento
        expense.setStatus(dto.getStatus() != null ? dto.getStatus() : ExpenseStatus.PENDING);
        expense.setCategory(category);
        expense.setUser(user);

        // Não definimos creation_date e last_update aqui,
        // pois são gerenciados automaticamente pelo Hibernate com @CreationTimestamp e @UpdateTimestamp.

        return expense;
    }

    /**
     * Converte uma lista de entidades Expense para uma lista de ExpenseDTOs.
     */
    public static List<ExpenseDTO> toDTOList(List<Expense> expenses) {
        return expenses.stream()
                .map(ExpenseMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza uma entidade Expense existente com dados de um ExpenseDTO.
     * Ideal para operações de atualização (PUT/PATCH).
     */
    public static Expense updateEntityFromDTO(Expense existingExpense, ExpenseDTO dto, Category category) {
        if (dto == null || existingExpense == null) {
            return existingExpense;
        }

        // Atualiza os campos apenas se eles forem fornecidos no DTO
        if (dto.getName() != null) {
            existingExpense.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            existingExpense.setDescription(dto.getDescription());
        }
        if (dto.getValue() != null) {
            existingExpense.setValue(dto.getValue());
        }
        if (dto.getVencimento() != null) {
            existingExpense.setVencimento(dto.getVencimento());
        }
        if (dto.getStatus() != null) {
            existingExpense.setStatus(dto.getStatus());
        }
        if (category != null) {
            existingExpense.setCategory(category);
        }

        // O usuário e a data de criação não devem ser alterados aqui.
        // O @UpdateTimestamp cuidará de last_update automaticamente.

        return existingExpense;
    }
}