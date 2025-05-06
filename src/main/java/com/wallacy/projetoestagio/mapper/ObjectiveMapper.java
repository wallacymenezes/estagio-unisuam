package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.ObjectiveDTO;
import com.wallacy.projetoestagio.model.Objective;
import com.wallacy.projetoestagio.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectiveMapper {

    /**
     * Converts an Objective entity to ObjectiveDTO
     *
     * @param objective the entity to convert
     * @return the DTO representation
     */
    public static ObjectiveDTO toDTO(Objective objective) {
        if (objective == null) {
            return null;
        }

        return new ObjectiveDTO(
                objective.getId(),
                objective.getName(),
                objective.getTarget(),
                objective.getTerm(),
                objective.getCreation_date()
        );
    }

    /**
     * Converts an ObjectiveDTO to Objective entity
     *
     * @param objectiveDTO the DTO to convert
     * @param user the user associated with the objective
     * @return the entity representation
     */
    public static Objective toEntity(ObjectiveDTO objectiveDTO, User user) {
        if (objectiveDTO == null) {
            return null;
        }

        Objective objective = new Objective();
        objective.setId(objectiveDTO.getId());
        objective.setName(objectiveDTO.getName());
        objective.setTarget(objectiveDTO.getTarget());
        objective.setTerm(objectiveDTO.getTerm());
        objective.setCreation_date(objectiveDTO.getCreationDate());
        objective.setUser(user);

        return objective;
    }

    /**
     * Converts a list of Objective entities to a list of ObjectiveDTOs
     *
     * @param objectives the list of entities to convert
     * @return the list of DTO representations
     */
    public static List<ObjectiveDTO> toDTOList(List<Objective> objectives) {
        return objectives.stream()
                .map(ObjectiveMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing Objective entity with data from a DTO
     *
     * @param existing the existing entity to update
     * @param objectiveDTO the DTO containing the new data
     * @return the updated entity
     */
    public static Objective updateEntityFromDTO(Objective existing, ObjectiveDTO objectiveDTO) {
        if (objectiveDTO == null) {
            return existing;
        }

        if (objectiveDTO.getName() != null) {
            existing.setName(objectiveDTO.getName());
        }
        if (objectiveDTO.getTarget() != null) {
            existing.setTarget(objectiveDTO.getTarget());
        }
        if (objectiveDTO.getTerm() != null) {
            existing.setTerm(objectiveDTO.getTerm());
        }

        return existing;
    }
}
