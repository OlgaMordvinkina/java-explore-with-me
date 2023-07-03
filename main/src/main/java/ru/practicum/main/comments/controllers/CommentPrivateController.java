package ru.practicum.main.comments.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.dto.NewCommentDto;
import ru.practicum.main.comments.dto.UpdateCommentDto;
import ru.practicum.main.comments.services.CommentService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events/{eventId}/comments")
public class CommentPrivateController {
    private final CommentService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentDto addComment(@PathVariable Long userId,
                                 @PathVariable Long eventId,
                                 @Valid @RequestBody NewCommentDto commentDto) {
        log.info("Получен запрос POST /users/{userId}/events/{eventId}/comments");
        return service.addComment(eventId, userId, commentDto);
    }

    @PatchMapping("/{commentId}")
    public UpdateCommentDto updateComment(@PathVariable Long userId,
                                          @PathVariable Long eventId,
                                          @PathVariable Long commentId,
                                          @Valid @RequestBody NewCommentDto commentDto) {
        log.info("Получен запрос PATCH /users/{userId}/events/{eventId}/comments");
        return service.updateComment(eventId, userId, commentId, commentDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long userId,
                              @PathVariable Long eventId,
                              @PathVariable Long commentId) {
        log.info("Получен запрос DELETE /users/{userId}/events/{eventId}/comments");
        service.deleteComment(userId, eventId, commentId);
    }


    @GetMapping
    public List<CommentDto> getCommentsByUserId(@PathVariable Long userId,
                                                @PathVariable Long eventId) {
        log.info("Получен запрос GET /users/{userId}/events/{eventId}/comments");
        return service.getCommentsByUserId(eventId, userId);
    }
}
