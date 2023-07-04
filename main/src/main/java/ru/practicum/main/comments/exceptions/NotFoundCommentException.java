package ru.practicum.main.comments.exceptions;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class NotFoundCommentException extends NoSuchElementException {
    private final Long id;
    private final String field = "Comment";

    public NotFoundCommentException(Long id) {
        super("Comment с ID " + id + " нет.");
        this.id = id;
    }
}
