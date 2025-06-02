package com.wallacy.projetoestagio.dto;

import com.wallacy.projetoestagio.enums.ExpenseStatus;
import jakarta.validation.constraints.NotNull;

public class ExpenseStatusUpdateDTO {

    @NotNull(message = "O ID da despesa é obrigatório para atualização.")
    private Long id;

    @NotNull(message = "O status da despesa é obrigatório.")
    private ExpenseStatus status;

    public ExpenseStatusUpdateDTO() {
    }

    public ExpenseStatusUpdateDTO(Long id, ExpenseStatus status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }
}