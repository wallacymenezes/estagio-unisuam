package com.wallacy.projetoestagio.dto;

import java.util.List;
import java.util.UUID;

public class UserDTO {

    private UUID userId;
    private String name;
    private String email;
    private String photo;

    private List<EarningDTO> earnings;
    private List<ExpenseDTO> expenses;
    private List<InvestmentDTO> investments;
    private List<ObjectiveDTO> objectives;

    // Constructors

    public UserDTO() {
    }

    public UserDTO(UUID userId, String name, String email, String photo) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    // Getters e Setters

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<EarningDTO> getEarnings() {
        return earnings;
    }

    public void setEarnings(List<EarningDTO> earnings) {
        this.earnings = earnings;
    }

    public List<ExpenseDTO> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDTO> expenses) {
        this.expenses = expenses;
    }

    public List<InvestmentDTO> getInvestments() {
        return investments;
    }

    public void setInvestments(List<InvestmentDTO> investments) {
        this.investments = investments;
    }

    public List<ObjectiveDTO> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<ObjectiveDTO> objectives) {
        this.objectives = objectives;
    }
}
