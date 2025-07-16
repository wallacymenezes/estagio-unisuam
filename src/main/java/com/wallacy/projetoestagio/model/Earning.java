package com.wallacy.projetoestagio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_earnings")
public class Earning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name cannot be empty")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters")
    private String name;

    @NotNull(message = "The value is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Invalid value. Maximum of 10 whole digits and 2 decimals.")
    private BigDecimal value;

    @Size(max = 1000, message = "The description must be less than 1000 characters")
    private String description;

    private LocalDateTime recebimento;

    private boolean wage;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("earnings")
    private User user;

    @CreationTimestamp
    private LocalDateTime creation_date;

    @UpdateTimestamp
    private LocalDateTime last_update;

    // ## CONSTRUCTORS ##
    public Earning() {
    }

    public Earning(Long id, String name, BigDecimal value, String description, LocalDateTime recebimento, boolean wage, User user, LocalDateTime creation_date, LocalDateTime last_update) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.description = description;
        this.recebimento = recebimento;
        this.wage = wage;
        this.user = user;
        this.creation_date = creation_date;
        this.last_update = last_update;
    }

    // ## GETTERS ##
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getRecebimento() {
        return recebimento;
    }

    public boolean isWage() {
        return wage;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public LocalDateTime getLast_update() {
        return last_update;
    }

    // ## SETTERS ##
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecebimento(LocalDateTime recebimento) {
        this.recebimento = recebimento;
    }

    public void setWage(boolean wage) {
        this.wage = wage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public void setLast_update(LocalDateTime last_update) {
        this.last_update = last_update;
    }
}