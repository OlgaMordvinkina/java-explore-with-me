package ru.practicum.main.category.exceptions;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class NotFoundCategoryException extends NoSuchElementException {
    private final Long id;
    private final String field = "Category";

    public NotFoundCategoryException(Long id) {
        super("Category с ID " + id + " нет.");
        this.id = id;
    }
}
