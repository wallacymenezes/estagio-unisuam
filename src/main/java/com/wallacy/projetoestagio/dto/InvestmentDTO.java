package com.wallacy.projetoestagio.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class InvestmentDTO {

    private Long id;
    private String name;
    private String description;
    private double percentage;
    private int months;
    private String creation_date; // trocar String por LocalDateTime
    private BigDecimal value;
    private Long objectiveId;
    private String investmentType;
    private UUID userId;

    public InvestmentDTO() {}

    public InvestmentDTO(Long id, String name, String description, double percentage, int months,
                         String creation_date, BigDecimal value, Long objectiveId, String investmentType, UUID userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.percentage = percentage;
        this.months = months;
        this.creation_date = creation_date;
        this.value = value;
        this.objectiveId = objectiveId;
        this.investmentType = investmentType;
        this.userId = userId;
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

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public Long getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Long objectiveId) {
        this.objectiveId = objectiveId;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
