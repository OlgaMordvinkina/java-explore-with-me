package ru.practicum.main.category.mapper;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.models.Category;

public class CategoryMapper {
    public static Category toCategory(NewCategoryDto categoryDto) {
        return new Category(
                null,
                categoryDto.getName()
        );
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                (category.getId() != null ? category.getId() : null),
                category.getName()
        );
    }
}
