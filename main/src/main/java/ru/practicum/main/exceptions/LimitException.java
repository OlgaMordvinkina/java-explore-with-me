package ru.practicum.main.exceptions;

public class LimitException extends RuntimeException {
    public LimitException(String message) {
        super(message);
    }

    public LimitException() {
        super();
    }
}
