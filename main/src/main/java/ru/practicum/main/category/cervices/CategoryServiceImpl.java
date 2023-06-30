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
import ru.practicum.main.event.models.Event;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.exceptions.NotAvailableException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategory) {
        log.info("Добавлена Category: {}", newCategory);
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(newCategory)));
    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = existById(catId);
        List<Event> events = eventRepository.findAllByCategoryId(catId);
        if (!events.isEmpty()) {
            throw new NotAvailableException();
        }
        log.info("Удалена Category: {}", category);
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        Category category = existById(catId);
        category.setName(categoryDto.getName());
        log.info("Обновлена Category: {}", category);
        Category save = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(save);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        List<CategoryDto> categories = categoryRepository.findAll(PageRequest.of(from / size, size)).stream()
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
        return categoryRepository.findById(catId).orElseThrow(() -> new NotFoundCategoryException(catId));
    }
}
