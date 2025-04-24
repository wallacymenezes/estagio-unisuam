package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.ExpenseDTO;
import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.Expense;

public class ExpenseMapper {

    public static ExpenseDTO toDTO(Expense expense) {
        if (expense == null) {
            return null;
        }

        return new ExpenseDTO(
                expense.getId(),
                expense.getName(),
                expense.getDescription(),
                expense.getValue(),
                expense.getCreation_date(),
                expense.getCategory() != null ? expense.getCategory().getName() : null
        );
    }

    public static Expense toEntity(ExpenseDTO dto, Category category) {
        if (dto == null) {
            return null;
        }

        Expense expense = new Expense();
        expense.setId(dto.getId());
        expense.setName(dto.getName());
        expense.setDescription(dto.getDescription());
        expense.setValue(dto.getValue());
        expense.setCreation_date(dto.getCreationDate());
        expense.setCategory(category); // categoria já buscada previamente

        return expense;
    }
}
