package ru.practicum.main.comments.services;

import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.dto.NewCommentDto;
import ru.practicum.main.comments.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto addComment(Long eventId, Long userId, NewCommentDto newComment);

    UpdateCommentDto updateComment(Long eventId, Long userId, Long commentId, NewCommentDto updateComment);

    void deleteComment(Long userId, Long eventId, Long commentId);

    List<CommentDto> getCommentsByEventId(Long eventId);

    CommentDto getCommentsByCommentId(Long eventId, Long commentId);

    List<CommentDto> getCommentsByUserId(Long eventId, Long userId);
}
