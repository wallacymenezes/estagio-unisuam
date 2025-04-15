package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.EarningDTO;
import com.wallacy.projetoestagio.model.Earning;
import com.wallacy.projetoestagio.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class EarningMapper {

    /**
     * Converts an Earning entity to EarningDTO
     *
     * @param earning the entity to convert
     * @return the DTO representation
     */
    public static EarningDTO toDTO(Earning earning) {
        if (earning == null) {
            return null;
        }

        return new EarningDTO(
                earning.getId(),
                earning.getName(),
                earning.getDescription(),
                earning.getValue(),
                earning.isWage(),
                earning.getCreation_date()
        );
    }

    /**
     * Converts an EarningDTO to Earning entity
     *
     * @param earningDTO the DTO to convert
     * @param user the user associated with the earning
     * @return the entity representation
     */
    public static Earning toEntity(EarningDTO earningDTO, User user) {
        if (earningDTO == null) {
            return null;
        }

        Earning earning = new Earning();
        earning.setId(earningDTO.getId());
        earning.setName(earningDTO.getName());
        earning.setDescription(earningDTO.getDescription());
        earning.setValue(earningDTO.getValue());
        earning.setWage(earningDTO.isWage());
        earning.setCreation_date(earningDTO.getCreationDate());
        earning.setUser(user);

        return earning;
    }

    /**
     * Converts a list of Earning entities to a list of EarningDTOs
     *
     * @param earnings the list of entities to convert
     * @return the list of DTO representations
     */
    public static List<EarningDTO> toDTOList(List<Earning> earnings) {
        return earnings.stream()
                .map(EarningMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing Earning entity with data from a DTO
     *
     * @param existing the existing entity to update
     * @param earningDTO the DTO containing the new data
     * @return the updated entity
     */
    public static Earning updateEntityFromDTO(Earning existing, EarningDTO earningDTO) {
        if (earningDTO == null) {
            return existing;
        }

        if (earningDTO.getName() != null) {
            existing.setName(earningDTO.getName());
        }
        if (earningDTO.getDescription() != null) {
            existing.setDescription(earningDTO.getDescription());
        }
        if (earningDTO.getValue() != null) {
            existing.setValue(earningDTO.getValue());
        }
        existing.setWage(earningDTO.isWage());
        // Normalmente não atualizamos a data de criação

        return existing;
    }
}