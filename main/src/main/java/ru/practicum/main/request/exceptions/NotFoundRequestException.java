package ru.practicum.main.request.exceptions;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class NotFoundRequestException extends NoSuchElementException {
    private final Long id;
    private final String field = "Request";

    public NotFoundRequestException(Long id) {
        super("Request с ID " + id + " нет.");
        this.id = id;
    }
}
