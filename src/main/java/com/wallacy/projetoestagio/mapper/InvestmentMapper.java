package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.InvestmentDTO;
import com.wallacy.projetoestagio.model.Investment;
import com.wallacy.projetoestagio.model.Objective;
import com.wallacy.projetoestagio.model.User;

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
                investment.getCreation_date(),  // Passa Timestamp diretamente
                investment.getValue(),
                investment.getObjective() != null ? investment.getObjective().getId() : null,
                investment.getType() != null ? investment.getType().getLabel() : null,
                investment.getUser() != null ? investment.getUser().getUserId() : null
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
        investment.setCreation_date(dto.getCreation_date());  // Timestamp direto
        investment.setValue(dto.getValue());
        investment.setUser(user);

        if (dto.getInvestmentType() != null) {
            try {
                investment.setType(Investment.InvestmentType.valueOf(dto.getInvestmentType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                investment.setType(null); // ou lance exceção, conforme regra de negócio
            }
        } else {
            investment.setType(null);
        }

        investment.setObjective(objective);

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
            try {
                existing.setType(Investment.InvestmentType.valueOf(dto.getInvestmentType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // mantém ou limpa tipo, conforme regra
            }
        }

        existing.setObjective(objective); // pode ser null para desvincular

        return existing;
    }
}
