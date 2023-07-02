package ru.practicum.main.event.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.services.EventService;
import ru.practicum.main.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class EventPrivateController {
    private final EventService service;

    @GetMapping
    public List<EventShortDto> getEventsUser(@PathVariable Long userId,
                                             @RequestParam(defaultValue = "0") int from,
                                             @RequestParam(defaultValue = "10") int size) {
        log.info("Получен запрос GET /users/{userId}/events");
        return service.getEventsUser(userId, from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EventFullDto addEvent(@PathVariable Long userId,
                                 @Valid @RequestBody NewEventDto newEvent) {
        log.info("Получен запрос POST /users/{userId}/events");
        return service.addEvent(userId, newEvent);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventUser(@PathVariable Long userId,
                                     @PathVariable Long eventId) {
        log.info("Получен запрос GET /users/{userId}/events/{eventId}");
        return service.getEventUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId,
                                    @Valid @RequestBody UpdateEventUserRequest request) {
        log.info("Получен запрос PATCH /users/{userId}/events/{eventId}");
        return service.updateEvent(userId, eventId, request);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getEventUserParticipation(@PathVariable Long userId,
                                                                   @PathVariable Long eventId) {
        log.info("Получен запрос GET /users/{userId}/events/{eventId}/requests");
        return service.getEventUserParticipation(userId, eventId);
    }


    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult setStatusEvent(@PathVariable Long userId,
                                                         @PathVariable Long eventId,
                                                         @RequestBody EventRequestStatusUpdateRequest request) {
        log.info("Получен запрос PATCH /users/{userId}/events/{eventId}/requests");
        return service.setStatusEvent(userId, eventId, request);
    }
}

