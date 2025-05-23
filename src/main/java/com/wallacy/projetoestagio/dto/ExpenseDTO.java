package com.wallacy.projetoestagio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ExpenseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal value;
    private LocalDateTime creationDate;
    private Long category;
    private UUID userId;  // Novo campo para armazenar o id do usuário

    // Constructors

    public ExpenseDTO() {
    }

    public ExpenseDTO(Long id, String name, String description, BigDecimal value, LocalDateTime creationDate, Long category, UUID userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.creationDate = creationDate;
        this.category = category;
        this.userId = userId;
    }

    // Getters and Setters

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
