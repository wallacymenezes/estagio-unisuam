package com.wallacy.projetoestagio.dto;

import com.wallacy.projetoestagio.enums.ExpenseStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ExpenseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal value;
    private LocalDate vencimento;   // Novo campo
    private ExpenseStatus status;
    private Long categoryId;
    private UUID userId;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdate;   // Novo campo

    // ## CONSTRUCTORS ##

    public ExpenseDTO() {
    }

    public ExpenseDTO(Long id, String name, String description, BigDecimal value, LocalDate vencimento, ExpenseStatus status, Long categoryId, UUID userId, LocalDateTime creationDate, LocalDateTime lastUpdate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.vencimento = vencimento;
        this.status = status;
        this.categoryId = categoryId;
        this.userId = userId;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
    }

    // ## GETTERS E SETTERS ##

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}