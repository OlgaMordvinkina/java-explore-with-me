package ru.practicum.main.exceptions;

import lombok.Getter;

@Getter
public class AccessException extends RuntimeException {
    private final Long id;
    private final String field;
    private final String message;

    public AccessException(String message) {
        super(message);
        this.id = null;
        this.field = null;
        this.message = message;
    }

    public AccessException(Long id, String field) {
        super("У вас нет доступа к " + field + " с ID " + id + ".");
        this.id = id;
        this.field = field;
        this.message = null;
    }
}
