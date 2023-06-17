package ru.practicum.service.exceptions;

import lombok.Getter;

import javax.xml.bind.ValidationException;

@Getter
public class NotValidDataException extends ValidationException {
    private final String field;

    public NotValidDataException(String field) {
        super("Ошибка при заполнении поля");
        this.field = field;
    }
}
