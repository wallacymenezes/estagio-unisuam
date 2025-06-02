package com.wallacy.projetoestagio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wallacy.projetoestagio.enums.ExpenseStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
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

    @Size(min = 1, max = 1000, message = "The description must be less than 1000 characters")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Invalid value. Maximum of 10 whole digits and 2 decimals.")
    @NotNull(message = "The value is mandatory")
    private BigDecimal value;

    @CreationTimestamp
    private LocalDateTime creation_date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("expenses")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("spending_by_category")
    private Category category;

    // NOVO ATRIBUTO: Status da despesa
    @Enumerated(EnumType.STRING) // Armazena o nome do Enum como String no banco de dados
    @Column(nullable = false)
    private ExpenseStatus status; //

    // Constructors

    public Expense() {
        this.status = ExpenseStatus.PENDING; // Define um status padrão ao criar uma nova despesa
    }

    public Expense(Long id, String name, String description, BigDecimal value, LocalDateTime creation_date, User user, Category category, ExpenseStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.creation_date = creation_date;
        this.user = user;
        this.category = category;
        this.status = status; //
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

    public @Size(min = 1, max = 1000, message = "The description must be less than 1000 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 1, max = 1000, message = "The description must be less than 1000 characters") String description) {
        this.description = description;
    }

    public @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero") @Digits(integer = 10, fraction = 2, message = "Invalid value. Maximum of 10 whole digits and 2 decimals.") @NotNull(message = "The value is mandatory") BigDecimal getValue() {
        return value;
    }

    public void setValue(@DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than zero") @Digits(integer = 10, fraction = 2, message = "Invalid value. Maximum of 10 whole digits and 2 decimals.") @NotNull(message = "The value is mandatory") BigDecimal value) {
        this.value = value;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // NOVO GETTER E SETTER para o status
    public ExpenseStatus getStatus() { //
        return status;
    }

    public void setStatus(ExpenseStatus status) { //
        this.status = status;
    }
}