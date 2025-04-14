package com.wallacy.projetoestagio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ObjectiveDTO {

    private Long id;
    private String name;
    private BigDecimal target;
    private LocalDate term;
    private LocalDateTime creationDate;

    // Constructors

    public ObjectiveDTO() {
    }

    public ObjectiveDTO(Long id, String name, BigDecimal target, LocalDate term, LocalDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.target = target;
        this.term = term;
        this.creationDate = creationDate;
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

    public BigDecimal getTarget() {
        return target;
    }

    public void setTarget(BigDecimal target) {
        this.target = target;
    }

    public LocalDate getTerm() {
        return term;
    }

    public void setTerm(LocalDate term) {
        this.term = term;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
