package ru.practicum.main.comments.mapper;

import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.dto.NewCommentDto;
import ru.practicum.main.comments.dto.UpdateCommentDto;
import ru.practicum.main.comments.models.Comment;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.user.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentMapper {
    private static final String format = "yyyy-MM-dd HH:mm:ss";

    public static Comment toComment(NewCommentDto commentDto, Event event, User author) {
        return new Comment(
                null,
                event,
                author,
                commentDto.getText(),
                getLocalDate(),
                null
        );
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getAuthor().getName(),
                comment.getText(),
                comment.getCreatedDate()
        );
    }

    public static UpdateCommentDto toUpdateCommentDto(Comment comment) {
        return new UpdateCommentDto(
                comment.getId(),
                comment.getAuthor().getName(),
                comment.getText(),
                comment.getCreatedDate(),
                comment.getUpdateDate()
        );
    }

    private static LocalDateTime getLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
    }
}
