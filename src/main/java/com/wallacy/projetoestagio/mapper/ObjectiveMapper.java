package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.ObjectiveDTO;
import com.wallacy.projetoestagio.model.Objective;
import com.wallacy.projetoestagio.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectiveMapper {

    public static ObjectiveDTO toDTO(Objective objective) {
        if (objective == null) {
            return null;
        }

        return new ObjectiveDTO(
                objective.getId(),
                objective.getName(),
                objective.getTarget(),
                objective.getTerm(),
                objective.getCreation_date(),
                objective.getUser() != null ? objective.getUser().getUserId() : null
        );
    }

    public static Objective toEntity(ObjectiveDTO dto, User user) {
        if (dto == null) {
            return null;
        }

        Objective objective = new Objective();
        objective.setId(dto.getId());
        objective.setName(dto.getName());
        objective.setTarget(dto.getTarget());
        objective.setTerm(dto.getTerm());
        objective.setCreation_date(dto.getCreationDate());
        objective.setUser(user);

        return objective;
    }

    public static List<ObjectiveDTO> toDTOList(List<Objective> objectives) {
        return objectives.stream()
                .map(ObjectiveMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Objective updateEntityFromDTO(Objective existing, ObjectiveDTO dto) {
        if (dto == null) {
            return existing;
        }

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }

        if (dto.getTarget() != null) {
            existing.setTarget(dto.getTarget());
        }

        if (dto.getTerm() != null) {
            existing.setTerm(dto.getTerm());
        }

        // Normalmente não atualizamos creationDate nem usuário aqui

        return existing;
    }
}
