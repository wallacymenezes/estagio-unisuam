package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.InvestmentDTO;
import com.wallacy.projetoestagio.model.Investment;
import com.wallacy.projetoestagio.model.Objective;
import com.wallacy.projetoestagio.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class InvestmentMapper {

    /**
     * Converte uma entidade Investment para um InvestmentDTO.
     * Inclui o campo last_update para uma visão completa.
     */
    public static InvestmentDTO toDTO(Investment investment) {
        if (investment == null) {
            return null;
        }

        // Supondo que InvestmentDTO foi atualizado para incluir lastUpdate
        return new InvestmentDTO(
                investment.getId(),
                investment.getName(),
                investment.getDescription(),
                investment.getPercentage(),
                investment.getMonths(),
                investment.getValue(),
                investment.getType() != null ? investment.getType().getLabel() : null,
                investment.getObjective() != null ? investment.getObjective().getId() : null,
                investment.getUser() != null ? investment.getUser().getUserId() : null,
                investment.getCreation_date(),
                investment.getLast_update() // Incluído
        );
    }

    /**
     * Converte um InvestmentDTO para a entidade Investment.
     * Ideal para criar uma nova entidade a partir de dados da API.
     */
    public static Investment toEntity(InvestmentDTO dto, User user, Objective objective) {
        if (dto == null) {
            return null;
        }

        Investment investment = new Investment();
        investment.setId(dto.getId()); // Será null para uma nova entidade
        investment.setName(dto.getName());
        investment.setDescription(dto.getDescription());
        investment.setPercentage(dto.getPercentage());
        investment.setMonths(dto.getMonths());
        investment.setValue(dto.getValue());
        investment.setUser(user);
        investment.setObjective(objective);

        // Converte a String do DTO para o Enum da entidade
        if (dto.getInvestmentType() != null) {
            try {
                investment.setType(Investment.InvestmentType.valueOf(dto.getInvestmentType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Se o tipo for inválido, define como nulo ou lança uma exceção,
                // dependendo da sua regra de negócio.
                investment.setType(null);
            }
        }

        // Não definimos creation_date e last_update aqui,
        // pois são gerenciados automaticamente pelo Hibernate com @CreationTimestamp e @UpdateTimestamp.

        return investment;
    }

    /**
     * Converte uma lista de entidades Investment para uma lista de InvestmentDTOs.
     */
    public static List<InvestmentDTO> toDTOList(List<Investment> investments) {
        return investments.stream()
                .map(InvestmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza uma entidade Investment existente com dados de um InvestmentDTO.
     * Ideal para operações de atualização (PUT/PATCH).
     */
    public static Investment updateEntityFromDTO(Investment existingInvestment, InvestmentDTO dto, Objective objective) {
        if (dto == null || existingInvestment == null) {
            return existingInvestment;
        }

        // Atualiza os campos apenas se eles forem fornecidos no DTO
        if (dto.getName() != null) {
            existingInvestment.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            existingInvestment.setDescription(dto.getDescription());
        }
        if (dto.getPercentage() > 0) { // Considera 0 como valor a ser ignorado
            existingInvestment.setPercentage(dto.getPercentage());
        }
        if (dto.getMonths() > 0) { // Considera 0 como valor a ser ignorado
            existingInvestment.setMonths(dto.getMonths());
        }
        if (dto.getValue() != null) {
            existingInvestment.setValue(dto.getValue());
        }

        // Atualiza o tipo se fornecido
        if (dto.getInvestmentType() != null) {
            try {
                existingInvestment.setType(Investment.InvestmentType.valueOf(dto.getInvestmentType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Não altera o tipo se o valor do DTO for inválido
            }
        }

        // Permite a atualização do objetivo
        existingInvestment.setObjective(objective);

        // O usuário, data de criação e última atualização não devem ser alterados aqui.
        // O @UpdateTimestamp cuidará de last_update automaticamente.

        return existingInvestment;
    }
}