package ru.practicum.main.event.exceptions;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class NotFoundEventException extends NoSuchElementException {
    private final Long id;
    private final String field = "Event";

    public NotFoundEventException(Long id) {
        super("Event с ID " + id + " нет.");
        this.id = id;
    }
}
