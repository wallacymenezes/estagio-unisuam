package com.wallacy.projetoestagio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.*;

@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @NotEmpty(message = "The name cannot be empty")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "The CPF cannot be empty")
    @Min(value = 8, message = "The CPF must be 8 digits")
    private String password;

    @Max(value = 5000, message = "The Photo must be less than 5000 characters")
    private String photo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("user")
    private List<Earning> earnings;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("user")
    private List<Expense> expenses;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("user")
    private List<Investment> investments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Objective> objectives;

    // Getters, setters e construtores

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public @NotEmpty(message = "The name cannot be empty") @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name cannot be empty") @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters") String name) {
        this.name = name;
    }

    public @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "The CPF cannot be empty") @Min(value = 8, message = "The CPF must be 8 digits") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "The CPF cannot be empty") @Min(value = 8, message = "The CPF must be 8 digits") String password) {
        this.password = password;
    }

    public @Max(value = 5000, message = "The Photo must be less than 5000 characters") String getPhoto() {
        return photo;
    }

    public void setPhoto(@Max(value = 5000, message = "The Photo must be less than 5000 characters") String photo) {
        this.photo = photo;
    }

    public List<Earning> getEarnings() {
        return earnings;
    }

    public void setEarnings(List<Earning> earnings) {
        this.earnings = earnings;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }
}
