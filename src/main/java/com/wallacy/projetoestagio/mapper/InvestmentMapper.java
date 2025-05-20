package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.InvestmentDTO;
import com.wallacy.projetoestagio.model.Investment;
import com.wallacy.projetoestagio.model.Objective;
import com.wallacy.projetoestagio.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class InvestmentMapper {

    public static InvestmentDTO toDTO(Investment investment) {
        if (investment == null) {
            return null;
        }

        return new InvestmentDTO(
                investment.getId(),
                investment.getName(),
                investment.getDescription(),
                investment.getPercentage(),
                investment.getMonths(),
                investment.getCreation_date(),
                investment.getValue(),
                investment.getObjective() != null ? investment.getObjective().getId() : null,
                investment.getType().getLabel()
        );
    }

    public static Investment toEntity(InvestmentDTO dto, User user, Objective objective) {
        if (dto == null) {
            return null;
        }

        Investment investment = new Investment();
        investment.setId(dto.getId());
        investment.setName(dto.getName());
        investment.setDescription(dto.getDescription());
        investment.setPercentage(dto.getPercentage());
        investment.setMonths(dto.getMonths());
        investment.setCreation_date(dto.getCreation_date());
        investment.setValue(dto.getValue());
        investment.setUser(user);
        investment.setType(Investment.InvestmentType.valueOf(dto.getInvestmentType()));

        if (objective != null) {
            investment.setObjective(objective);
        }

        return investment;
    }

    public static Investment updateEntityFromDTO(Investment existing, InvestmentDTO dto, Objective objective) {
        if (dto == null) return existing;

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }

        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }

        if (dto.getPercentage() > 0) {
            existing.setPercentage(dto.getPercentage());
        }

        if (dto.getMonths() > 0) {
            existing.setMonths(dto.getMonths());
        }

        if (dto.getValue() != null) {
            existing.setValue(dto.getValue());
        }

        if (dto.getInvestmentType() != null) {
            existing.setType(Investment.InvestmentType.valueOf(dto.getInvestmentType()));
        }

        existing.setObjective(objective); // Pode ser null para desvincular

        return existing;
    }
}
