package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.InvestmentDTO;
import com.wallacy.projetoestagio.model.Investment;
import com.wallacy.projetoestagio.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class InvestmentMapper {

    /**
     * Converts an Investment entity to InvestmentDTO
     *
     * @param investment the entity to convert
     * @return the DTO representation
     */
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
                investment.getType().getLabel()
        );
    }

    /**
     * Converts an InvestmentDTO to Investment entity
     *
     * @param investmentDTO the DTO to convert
     * @param user the user associated with the investment
     * @return the entity representation
     */
    public static Investment toEntity(InvestmentDTO investmentDTO, User user) {
        if (investmentDTO == null) {
            return null;
        }

        Investment investment = new Investment();
        investment.setId(investmentDTO.getId());
        investment.setName(investmentDTO.getName());
        investment.setDescription(investmentDTO.getDescription());
        investment.setPercentage(investmentDTO.getPercentage());
        investment.setMonths(investmentDTO.getMonths());
        investment.setCreation_date(investmentDTO.getCreation_date());
        investment.setValue(investmentDTO.getValue());
        investment.setUser(user);
        investment.setType(Investment.InvestmentType.valueOf(investmentDTO.getInvestmentType()));

        return investment;
    }

    /**
     * Converts a list of Investment entities to a list of InvestmentDTOs
     *
     * @param investments the list of entities to convert
     * @return the list of DTO representations
     */
    public static List<InvestmentDTO> toDTOList(List<Investment> investments) {
        return investments.stream()
                .map(InvestmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing Investment entity with data from a DTO
     *
     * @param existing the existing entity to update
     * @param investmentDTO the DTO containing the new data
     * @return the updated entity
     */
    public static Investment updateEntityFromDTO(Investment existing, InvestmentDTO investmentDTO) {
        if (investmentDTO == null) {
            return existing;
        }

        if (investmentDTO.getName() != null) {
            existing.setName(investmentDTO.getName());
        }
        if (investmentDTO.getDescription() != null) {
            existing.setDescription(investmentDTO.getDescription());
        }
        if (investmentDTO.getPercentage() > 0) {
            existing.setPercentage(investmentDTO.getPercentage());
        }
        if (investmentDTO.getMonths() > 0) {
            existing.setMonths(investmentDTO.getMonths());
        }
        if (investmentDTO.getValue() != null) {
            existing.setValue(investmentDTO.getValue());
        }
        existing.setType(Investment.InvestmentType.valueOf(investmentDTO.getInvestmentType()));

        return existing;
    }
}
