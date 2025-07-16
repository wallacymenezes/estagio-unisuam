package com.wallacy.projetoestagio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class InvestmentDTO {

    private Long id;
    private String name;
    private String description;
    private double percentage;
    private int months;
    private BigDecimal value;
    private String investmentType;
    private Long objectiveId;
    private UUID userId;
    private LocalDateTime creationDate; // Corrigido de String para LocalDateTime
    private LocalDateTime lastUpdate;   // Novo campo

    // ## CONSTRUCTORS ##

    public InvestmentDTO() {
    }

    public InvestmentDTO(Long id, String name, String description, double percentage, int months, BigDecimal value, String investmentType, Long objectiveId, UUID userId, LocalDateTime creationDate, LocalDateTime lastUpdate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.percentage = percentage;
        this.months = months;
        this.value = value;
        this.investmentType = investmentType;
        this.objectiveId = objectiveId;
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

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public Long getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Long objectiveId) {
        this.objectiveId = objectiveId;
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