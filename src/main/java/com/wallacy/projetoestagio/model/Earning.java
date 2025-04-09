package com.wallacy.projetoestagio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Invalid value. Maximum of 10 whole digits and 2 decimals.")
    @NotNull(message = "The value is mandatory")
    private BigDecimal value;

    @Size(min = 1, max = 1000, message = "The description must be less than 1000 characters")
    private String description;

    @CreationTimestamp
    private LocalDateTime creation_date;

    private boolean wage;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("earnings")
    private User user;

    // Constructors

    public Earning() {
    }

    public Earning(Long id, String name, BigDecimal value, String description, LocalDateTime creation_date, boolean wage, User user) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.description = description;
        this.creation_date = creation_date;
        this.wage = wage;
        this.user = user;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "The name cannot be empty") @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The name cannot be empty") @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters") String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public @Size(min = 1, max = 1000, message = "The description must be less than 1000 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 1, max = 1000, message = "The description must be less than 1000 characters") String description) {
        this.description = description;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public boolean isWage() {
        return wage;
    }

    public void setWage(boolean wage) {
        this.wage = wage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
