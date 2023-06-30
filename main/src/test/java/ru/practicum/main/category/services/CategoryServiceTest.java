package ru.practicum.main.category.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.main.category.cervices.CategoryServiceImpl;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.exceptions.NotFoundCategoryException;
import ru.practicum.main.category.models.Category;
import ru.practicum.main.category.repositories.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository repository;
    @InjectMocks
    private CategoryServiceImpl service;
    public static final long id = 1L;
    private final Category category = new Category(1L, "nameCategory");
    private final CategoryDto categoryDto = new CategoryDto(id, "nameCategory");
    private final NewCategoryDto newCategoryDto = new NewCategoryDto("nameCategory");
    private final List<CategoryDto> categories = Collections.singletonList(categoryDto);

    @Test
    void addCategoryTest() {
        when(repository.save(any())).thenReturn(category);
        service.addCategory(newCategoryDto);
        verify(repository).save(any());
    }

    @Test
    void updateCategoryTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(category));
        when(repository.save(any())).thenReturn(category);

        CategoryDto expectedCategoryDto = service.updateCategory(1L, categoryDto);

        assertEquals(expectedCategoryDto, categoryDto);
        verify(repository).save(any());
    }

    @Test
    void updateCategory_returnNotFoundCategoryExceptionTest() {
        assertThrows(NotFoundCategoryException.class, () -> service.updateCategory(id, categoryDto));
    }

    @Test
    void deleteCategory_returnNotFoundCategoryExceptionTest() {
        assertThrows(NotFoundCategoryException.class, () -> service.deleteCategory(id));
    }

    @Test
    void getCategoriesTest() {
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(category)));

        List<CategoryDto> expectedCategories = service.getCategories(0, 10);

        assertEquals(expectedCategories, categories);

        assertNotNull(expectedCategories);
        assertFalse(expectedCategories.isEmpty());
    }

    @Test
    void getCategoryTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(category));

        CategoryDto expectedCategory = service.getCategory(id);

        assertEquals(categoryDto, expectedCategory);
    }

    @Test
    void getCategory_returnNotFoundCategoryExceptionTest() {
        assertThrows(NotFoundCategoryException.class, () -> service.updateCategory(id, categoryDto));
    }

}
