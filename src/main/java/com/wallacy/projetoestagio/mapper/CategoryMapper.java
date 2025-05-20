package com.wallacy.projetoestagio.mapper;

import com.wallacy.projetoestagio.dto.CategoryDTO;
import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.User;

import java.util.List;
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

    public static Category toEntity(CategoryDTO dto, User user) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setColor(dto.getColor());
        category.setUser(user);
        return category;
    }

    public static List<CategoryDTO> toDTOList(List<Category> categories) {
        return categories.stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
    }
}
