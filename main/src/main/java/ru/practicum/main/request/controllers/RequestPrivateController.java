package ru.practicum.main.request.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.request.cervices.RequestService;
import ru.practicum.main.request.dto.ParticipationRequestDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class RequestPrivateController {
    private final RequestService service;

    @GetMapping
    public List<ParticipationRequestDto> getRequestsUser(@PathVariable Long userId) {
        log.info("Получен запрос GET /users/{userId}/requests");
        return service.getRequestsUser(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ParticipationRequestDto addRequestUsers(@PathVariable Long userId,
                                                   @RequestParam Long eventId) throws AccessException {
        log.info("Получен запрос POST /users/{userId}/requests");
        return service.addRequestUsers(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto updateRequestUsers(@PathVariable Long userId,
                                                      @PathVariable Long requestId) {
        log.info("Получен запрос PATCH /users/{userId}/requests/{requestId}");
        return service.updateRequestUsers(userId, requestId);
    }
}

