package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.ExpenseDTO;
import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.Expense;
import com.wallacy.projetoestagio.enums.ExpenseStatus; // Importar o Enum
import com.wallacy.projetoestagio.model.User;

import java.util.UUID;

public class ExpenseMapper {

    public static ExpenseDTO toDTO(Expense expense) {
        if (expense == null) {
            return null;
        }

        UUID userId = null;
        if (expense.getUser() != null) {
            userId = expense.getUser().getUserId();
        }

        return new ExpenseDTO(
                expense.getId(),
                expense.getName(),
                expense.getDescription(),
                expense.getValue(),
                expense.getCreation_date(),
                expense.getCategory() != null ? expense.getCategory().getId() : null,
                userId,
                expense.getStatus() // Inclui o status ao converter para DTO
        );
    }

    public static Expense toEntity(ExpenseDTO dto, Category category, User user) {
        if (dto == null) {
            return null;
        }

        Expense expense = new Expense();
        expense.setId(dto.getId());
        expense.setName(dto.getName());
        expense.setDescription(dto.getDescription());
        expense.setValue(dto.getValue());
        expense.setCategory(category);
        expense.setUser(user);
        expense.setStatus(dto.getStatus() != null ? dto.getStatus() : ExpenseStatus.PENDING); // Define o status ou um padrão se for nulo

        return expense;
    }

    public static Expense updateEntityFromDTO(Expense existingExpense, ExpenseDTO dto, Category category) {
        if (existingExpense == null || dto == null) {
            return existingExpense;
        }

        existingExpense.setName(dto.getName());
        existingExpense.setDescription(dto.getDescription());
        existingExpense.setValue(dto.getValue());
        existingExpense.setCategory(category);

        // Atualiza o status se for fornecido no DTO
        if (dto.getStatus() != null) {
            existingExpense.setStatus(dto.getStatus());
        }

        // Normalmente não atualizamos o usuário aqui

        return existingExpense;
    }
}