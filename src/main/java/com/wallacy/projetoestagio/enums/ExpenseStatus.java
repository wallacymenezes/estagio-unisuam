package com.wallacy.projetoestagio.enums;

public enum ExpenseStatus {
    PENDING("Pendente"),
    PAID("Paga"),
    OVERDUE("Atrasada"),
    CANCELLED("Cancelada");

    private final String description;

    ExpenseStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}