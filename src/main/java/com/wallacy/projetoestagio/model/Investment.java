package com.wallacy.projetoestagio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_investments")
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The name cannot be empty")
    private String name;

    @Size(min = 1, max = 1000, message = "The description must be less than 1000 characters")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero")
    private double percentage;

    private int months;

    @CreationTimestamp
    private String creation_date;

    @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Invalid value. Maximum of 10 whole digits and 2 decimals.")
    @NotNull(message = "The value is mandatory")
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("investments")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvestmentType type;

    public enum InvestmentType {
        TESOURO("Tesouro Direto"),
        FIIS("Fundos Imobiliários"),
        ACOES("Ações"),
        POUPANCA("Poupança"),
        CDI("Certificado de Depósito Interbancário"),
        CRYPTO("Criptomoedas");

        private final String label;

        InvestmentType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    // Constructors

    public Investment() {
    }

    public Investment(Long id, String name, String description, double percentage, int months, String creation_date, BigDecimal value, User user, InvestmentType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.percentage = percentage;
        this.months = months;
        this.creation_date = creation_date;
        this.value = value;
        this.user = user;
        this.type = type;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "The name cannot be empty") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "The name cannot be empty") String name) {
        this.name = name;
    }

    public @Size(min = 1, max = 1000, message = "The description must be less than 1000 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 1, max = 1000, message = "The description must be less than 1000 characters") String description) {
        this.description = description;
    }

    @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero")
    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(@DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero") double percentage) {
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

    public @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero") @Digits(integer = 10, fraction = 2, message = "Invalid value. Maximum of 10 whole digits and 2 decimals.") @NotNull(message = "The value is mandatory") BigDecimal getValue() {
        return value;
    }

    public void setValue(@DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero") @Digits(integer = 10, fraction = 2, message = "Invalid value. Maximum of 10 whole digits and 2 decimals.") @NotNull(message = "The value is mandatory") BigDecimal value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public InvestmentType getType() {
        return type;
    }

    public void setType(InvestmentType type) {
        this.type = type;
    }
}
