package ru.practicum.main.category.cervices;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(NewCategoryDto newCategory);

    void deleteCategory(Long catId);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategory(Long catId);
}
