package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.EarningDTO;
import com.wallacy.projetoestagio.model.Earning;
import com.wallacy.projetoestagio.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class EarningMapper {

    /**
     * Converte uma entidade Earning para EarningDTO.
     * Inclui os novos campos recebimento e last_update.
     */
    public static EarningDTO toDTO(Earning earning) {
        if (earning == null) {
            return null;
        }

        // Assumindo que o EarningDTO também foi atualizado para incluir os novos campos.
        return new EarningDTO(
                earning.getId(),
                earning.getName(),
                earning.getDescription(),
                earning.getValue(),
                earning.isWage(),
                earning.getRecebimento(), // Novo campo
                earning.getCreation_date(),
                earning.getLast_update(), // Novo campo
                earning.getUser() != null ? earning.getUser().getUserId() : null
        );
    }

    /**
     * Converte um EarningDTO para a entidade Earning.
     * Ideal para criar uma nova entidade a partir de dados da API.
     */
    public static Earning toEntity(EarningDTO earningDTO, User user) {
        if (earningDTO == null) {
            return null;
        }

        Earning earning = new Earning();
        earning.setId(earningDTO.getId()); // Será null para uma nova entidade
        earning.setName(earningDTO.getName());
        earning.setDescription(earningDTO.getDescription());
        earning.setValue(earningDTO.getValue());
        earning.setWage(earningDTO.isWage());
        earning.setRecebimento(earningDTO.getRecebimento()); // Define a data de recebimento
        earning.setUser(user);

        // Não definimos creation_date e last_update aqui,
        // pois são gerenciados automaticamente pelo Hibernate com @CreationTimestamp e @UpdateTimestamp.

        return earning;
    }

    /**
     * Converte uma lista de entidades Earning para uma lista de EarningDTOs.
     */
    public static List<EarningDTO> toDTOList(List<Earning> earnings) {
        return earnings.stream()
                .map(EarningMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza uma entidade Earning existente com dados de um EarningDTO.
     * Ideal para operações de atualização (PUT/PATCH).
     */
    public static Earning updateEntityFromDTO(Earning existingEarning, EarningDTO earningDTO) {
        if (earningDTO == null || existingEarning == null) {
            return existingEarning;
        }

        // Atualiza os campos apenas se eles forem fornecidos no DTO
        if (earningDTO.getName() != null) {
            existingEarning.setName(earningDTO.getName());
        }
        if (earningDTO.getDescription() != null) {
            existingEarning.setDescription(earningDTO.getDescription());
        }
        if (earningDTO.getValue() != null) {
            existingEarning.setValue(earningDTO.getValue());
        }
        if (earningDTO.getRecebimento() != null) {
            existingEarning.setRecebimento(earningDTO.getRecebimento());
        }

        // O campo 'wage' é booleano, então geralmente é atualizado diretamente.
        existingEarning.setWage(earningDTO.isWage());

        // O usuário, data de criação e última atualização não devem ser alterados aqui.
        // O @UpdateTimestamp cuidará de last_update automaticamente.

        return existingEarning;
    }
}