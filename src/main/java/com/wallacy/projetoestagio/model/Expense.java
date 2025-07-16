package com.wallacy.projetoestagio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wallacy.projetoestagio.enums.ExpenseStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name cannot be empty")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters")
    private String name;

    @Size(max = 1000, message = "The description must be less than 1000 characters")
    private String description;

    @NotNull(message = "The value is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Invalid value. Maximum of 10 whole digits and 2 decimals.")
    private BigDecimal value;

    private LocalDate vencimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("expenses")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("spending_by_category")
    private Category category;

    @CreationTimestamp
    private LocalDateTime creation_date;

    @UpdateTimestamp
    private LocalDateTime last_update;


    // ## CONSTRUCTORS ##
    public Expense() {
        this.status = ExpenseStatus.PENDING; // Define um status padrão
    }

    public Expense(Long id, String name, String description, BigDecimal value, LocalDate vencimento, ExpenseStatus status, User user, Category category, LocalDateTime creation_date, LocalDateTime last_update) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.vencimento = vencimento;
        this.status = status;
        this.user = user;
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public void setLast_update(LocalDateTime last_update) {
        this.last_update = last_update;
    }
}