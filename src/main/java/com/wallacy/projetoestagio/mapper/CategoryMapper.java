package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.CategoryDTO;
import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) return null;

        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getColor(),
                category.getUser().getUserId()
        );
    }

    public static Category updateEntityFromDTO(Category category, CategoryDTO dto) {
        if (category == null || dto == null) return category;

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setColor(dto.getColor());
        // Não altera o usuário aqui, pois você está setando explicitamente depois
        return category;
    }


    public static Category toEntity(CategoryDTO dto, UserRepository userRepository) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setColor(dto.getColor());

        if (dto.getUserId() != null) {
            Optional<User> userOpt = userRepository.findByUserId(dto.getUserId());
            userOpt.ifPresent(category::setUser);
        }

        return category;
    }

    public static List<CategoryDTO> toDTOList(List<Category> categories) {
        return categories.stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
    }
}
