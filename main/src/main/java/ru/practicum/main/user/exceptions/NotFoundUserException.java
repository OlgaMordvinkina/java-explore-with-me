package ru.practicum.main.user.exceptions;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class NotFoundUserException extends NoSuchElementException {
    private final Long id;
    private final String field = "User";

    public NotFoundUserException(Long id) {
        super("User с ID " + id + " нет.");
        this.id = id;
    }
}
