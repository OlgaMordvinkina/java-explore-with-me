package ru.practicum.main.comments.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.dto.NewCommentDto;
import ru.practicum.main.comments.dto.UpdateCommentDto;
import ru.practicum.main.comments.exceptions.NotFoundCommentException;
import ru.practicum.main.comments.mapper.CommentMapper;
import ru.practicum.main.comments.models.Comment;
import ru.practicum.main.comments.repositories.CommentRepository;
import ru.practicum.main.event.enums.State;
import ru.practicum.main.event.exceptions.NotFoundEventException;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.exceptions.AccessException;
import ru.practicum.main.exceptions.NotAvailableException;
import ru.practicum.main.user.exceptions.NotFoundUserException;
import ru.practicum.main.user.models.User;
import ru.practicum.main.user.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public CommentDto addComment(Long eventId, Long userId, NewCommentDto newComment) {
        User user = existUserById(userId);
        Event event = existEventById(eventId);

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new NotAvailableException("Нельзя оставить комментарий о неопубликованном событии.");
        }

        Comment comment = CommentMapper.toComment(newComment, event, user);
        log.info("Добавлен Comment: {}", comment);
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public UpdateCommentDto updateComment(Long eventId, Long userId, Long commentId, NewCommentDto updateComment) {
        existUserById(userId);
        existEventById(eventId);
        Comment comment = existCommentById(commentId);
        checkOfAuthorship(userId, comment.getAuthor().getId(), commentId);

        if (updateComment.getText() != null) comment.setText(updateComment.getText());
        comment.setUpdateDate(getLocalDate());

        log.info("Изменён Comment: {}", comment);
        return CommentMapper.toUpdateCommentDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        existUserById(userId);
        existEventById(eventId);
        Comment comment = existCommentById(commentId);
        checkOfAuthorship(userId, comment.getAuthor().getId(), commentId);

        commentRepository.delete(comment);
        log.info("Удалён Comment: {}", comment);
    }

    @Override
    public List<CommentDto> getCommentsByEventId(Long eventId) {
        existEventById(eventId);
        List<Comment> comment = commentRepository.findAllByEventId(eventId);

        List<CommentDto> comments = comment.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
        log.info("Получены Comments: {}", comments);
        return comments;
    }

    @Override
    public CommentDto getCommentsByCommentId(Long eventId, Long commentId) {
        existEventById(eventId);
        Comment comment = existCommentById(commentId);
        CommentDto commentDto = CommentMapper.toCommentDto(comment);
        log.info("Получен Comment: {}", commentDto);
        return commentDto;
    }

    @Override
    public List<CommentDto> getCommentsByUserId(Long eventId, Long userId) {
        existEventById(eventId);
        List<Comment> comment = commentRepository.findAllByEventIdAndAuthorId(eventId, userId);

        List<CommentDto> comments = comment.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
        log.info("Получены Comments: {}", comments);
        return comments;
    }

    private Comment existCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundCommentException(commentId));
    }

    private Event existEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
    }

    private User existUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException(userId));
    }

    private void checkOfAuthorship(Long userId, Long ownerId, Long commentId) {
        if (!Objects.equals(userId, ownerId)) {
            throw new AccessException(commentId, "Comment");
        }
    }

    private LocalDateTime getLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
    }
}
