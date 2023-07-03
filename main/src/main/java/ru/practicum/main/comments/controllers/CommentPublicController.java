package ru.practicum.main.comments.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.services.CommentService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events/{eventId}/comments")
public class CommentPublicController {
    private final CommentService service;

    @GetMapping
    public List<CommentDto> getCommentsByEventId(@PathVariable Long eventId) {
        log.info("Получен запрос GET /events/{eventId}/comments");
        return service.getCommentsByEventId(eventId);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentsByCommentId(@PathVariable Long eventId,
                                             @PathVariable Long commentId) {
        log.info("Получен запрос GET /events/{eventId}/comments/{commentId}");
        return service.getCommentsByCommentId(eventId, commentId);
    }
}
