package ru.practicum.main.category.cervices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.exceptions.NotFoundCategoryException;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.models.Category;
import ru.practicum.main.category.repositories.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategory) {
        log.info("Добавлена Category: {}", newCategory);
        return CategoryMapper.toCategoryDto(repository.save(CategoryMapper.toCategory(newCategory)));
    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = existById(catId);
        log.info("Удалена Category: {}", category);
        repository.deleteById(catId);
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        Category category = existById(catId);
        category.setName(categoryDto.getName());
        log.info("Обновлена Category: {}", category);
        Category save = repository.save(category);
        return CategoryMapper.toCategoryDto(save);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        List<CategoryDto> categories = repository.findAll(PageRequest.of(from / size, size)).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
        log.info("Получены Categories: {}", categories);
        return categories;
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDto getCategory(Long catId) {
        Category category = existById(catId);
        log.info("Получена Category: {}", category);
        return CategoryMapper.toCategoryDto(category);
    }

    private Category existById(Long catId) {
        return repository.findById(catId).orElseThrow(() -> new NotFoundCategoryException(catId));
    }
}
