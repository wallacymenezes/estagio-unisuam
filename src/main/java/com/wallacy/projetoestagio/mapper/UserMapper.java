package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.*;
import com.wallacy.projetoestagio.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoto(user.getPhoto());
        dto.setRegistrationMethod(user.getRegistrationMethod());

        dto.setEarnings(user.getEarnings().stream().map(e -> {
            EarningDTO edto = new EarningDTO();
            edto.setId(e.getId());
            edto.setName(e.getName());
            edto.setValue(e.getValue());
            edto.setDescription(e.getDescription());
            edto.setCreationDate(e.getCreation_date());
            edto.setWage(e.isWage());
            return edto;
        }).collect(Collectors.toList()));

        dto.setExpenses(user.getExpenses().stream().map(e -> {
            ExpenseDTO exdto = new ExpenseDTO();
            exdto.setId(e.getId());
            exdto.setName(e.getName());
            exdto.setValue(e.getValue());
            exdto.setDescription(e.getDescription());
            exdto.setCreationDate(e.getCreation_date());
            exdto.setCategoryId(e.getCategory() != null ? e.getCategory().getId() : null);
            return exdto;
        }).collect(Collectors.toList()));

        dto.setInvestments(user.getInvestments().stream().map(inv -> {
            InvestmentDTO idto = new InvestmentDTO();
            idto.setId(inv.getId());
            idto.setName(inv.getName());
            idto.setDescription(inv.getDescription());
            idto.setPercentage(inv.getPercentage());
            idto.setMonths(inv.getMonths());
            idto.setCreationDate(inv.getCreation_date());
            idto.setValue(inv.getValue());
            idto.setInvestmentType(inv.getType().getLabel()); // Usando getLabel() do Enum
            return idto;
        }).collect(Collectors.toList()));

        dto.setObjectives(user.getObjectives().stream().map(o -> {
            ObjectiveDTO odto = new ObjectiveDTO();
            odto.setId(o.getId());
            odto.setName(o.getName());
            odto.setTarget(o.getTarget());
            odto.setTerm(o.getTerm());
            odto.setCreationDate(o.getCreation_date());
            return odto;
        }).collect(Collectors.toList()));

        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUserId(dto.getUserId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoto(dto.getPhoto());
        user.setPassword(dto.getPassword());
        user.setRegistrationMethod(dto.getRegistrationMethod());

        if (dto.getEarnings() != null) {
            List<Earning> earnings = dto.getEarnings().stream().map(e -> {
                Earning earning = new Earning();
                earning.setId(e.getId());
                earning.setName(e.getName());
                earning.setValue(e.getValue());
                earning.setDescription(e.getDescription());
                earning.setCreation_date(e.getCreationDate());
                earning.setWage(e.isWage());
                earning.setUser(user); // associa ao usuário
                return earning;
            }).collect(Collectors.toList());
            user.setEarnings(earnings);
        }

        if (dto.getExpenses() != null) {
            List<Expense> expenses = dto.getExpenses().stream().map(e -> {
                Expense expense = new Expense();
                expense.setId(e.getId());
                expense.setName(e.getName());
                expense.setValue(e.getValue());
                expense.setDescription(e.getDescription());
                expense.setCreation_date(e.getCreationDate());
                // Aqui assumimos que a categoria virá apenas com nome
                Category category = new Category();
                category.setId(e.getCategoryId());
                expense.setCategory(category);
                expense.setUser(user);
                return expense;
            }).collect(Collectors.toList());
            user.setExpenses(expenses);
        }

        if (dto.getInvestments() != null) {
            List<Investment> investments = dto.getInvestments().stream().map(i -> {
                Investment inv = new Investment();
                inv.setId(i.getId());
                inv.setName(i.getName());
                inv.setDescription(i.getDescription());
                inv.setPercentage(i.getPercentage());
                inv.setMonths(i.getMonths());
                inv.setCreation_date(i.getCreationDate());
                inv.setValue(i.getValue());
                inv.setType(Investment.InvestmentType.valueOf(i.getInvestmentType().toUpperCase())); // converte string para enum
                inv.setUser(user);
                return inv;
            }).collect(Collectors.toList());
            user.setInvestments(investments);
        }

        if (dto.getObjectives() != null) {
            List<Objective> objectives = dto.getObjectives().stream().map(o -> {
                Objective obj = new Objective();
                obj.setId(o.getId());
                obj.setName(o.getName());
                obj.setTarget(o.getTarget());
                obj.setTerm(o.getTerm());
                obj.setCreation_date(o.getCreationDate());
                obj.setUser(user);
                return obj;
            }).collect(Collectors.toList());
            user.setObjectives(objectives);
        }

        return user;
    }
}
