package ru.practicum.main.compilation.exceptions;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class NotFoundCompilationException extends NoSuchElementException {
    private final Long id;
    private final String field = "Compilation";

    public NotFoundCompilationException(Long id) {
        super("Compilation с ID " + id + " нет.");
        this.id = id;
    }
}
