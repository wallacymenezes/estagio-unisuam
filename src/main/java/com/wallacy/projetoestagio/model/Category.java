package com.wallacy.projetoestagio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "tb_categories")
public class Category {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The name cannot be empty")
    private String name;

    @Size(min = 1, max = 1000, message = "The description must be less than 1000 characters")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("category")
    private List<Expense> spending_by_category;

    // Constructors

    public Category() {
    }

    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public List<Expense> getSpending_by_category() {
        return spending_by_category;
    }

    public void setSpending_by_category(List<Expense> spending_by_category) {
        this.spending_by_category = spending_by_category;
    }
}
