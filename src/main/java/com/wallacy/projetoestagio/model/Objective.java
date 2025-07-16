package com.wallacy.projetoestagio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_objectives")
public class Objective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The name cannot be empty")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "Target must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Invalid target. Maximum of 10 whole digits and 2 decimals.")
    @NotNull(message = "The target is mandatory")
    private BigDecimal target;

    private LocalDate term;

    @UpdateTimestamp
    private LocalDateTime last_update;

    @CreationTimestamp
    private LocalDateTime creation_date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("objectives")
    private User user;

    // Constructors

    public Objective() {
    }

    public Objective(Long id, String name, BigDecimal target, LocalDate term, LocalDateTime creation_date, User user) {
        this.id = id;
        this.name = name;
        this.target = target;
        this.term = term;
        this.creation_date = creation_date;
        this.user = user;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "The name cannot be empty") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name cannot be empty") String name) {
        this.name = name;
    }

    public @DecimalMin(value = "0.0", inclusive = false, message = "Target must be greater than zero") @Digits(integer = 10, fraction = 2, message = "Invalid target. Maximum of 10 whole digits and 2 decimals.") @NotNull(message = "The target is mandatory") BigDecimal getTarget() {
        return target;
    }

    public void setTarget(@DecimalMin(value = "0.0", inclusive = false, message = "Target must be greater than zero") @Digits(integer = 10, fraction = 2, message = "Invalid target. Maximum of 10 whole digits and 2 decimals.") @NotNull(message = "The target is mandatory") BigDecimal target) {
        this.target = target;
    }

    public LocalDate getTerm() {
        return term;
    }

    public void setTerm(LocalDate term) {
        this.term = term;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
