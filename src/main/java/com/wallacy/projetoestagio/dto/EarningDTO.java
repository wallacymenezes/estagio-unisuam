package com.wallacy.projetoestagio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EarningDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal value;
    private boolean wage;
    private LocalDateTime creationDate;

    // Constructors

    public EarningDTO() {
    }

    public EarningDTO(Long id, String name, String description, BigDecimal value, boolean wage ,LocalDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.wage = wage;
        this.creationDate = creationDate;
    }

    // Getters e Setters

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

    public boolean isWage() {
        return wage;
    }

    public void setWage(boolean wage) {
        this.wage = wage;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
