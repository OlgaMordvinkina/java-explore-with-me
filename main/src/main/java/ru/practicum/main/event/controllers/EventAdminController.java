package ru.practicum.main.event.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFilterAdminDto;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.UpdateEventAdminRequest;
import ru.practicum.main.event.services.EventService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class EventAdminController {
    private final EventService service;

    @GetMapping
    public List<EventFullDto> getEventsFilterAdmin(@Valid EventFilterAdminDto filter) {
        log.info("Получен запрос GET /admin/events");
        return service.getEventsFilterAdmin(filter);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventFilterAdminById(@PathVariable Long eventId,
                                                   @Valid @RequestBody UpdateEventAdminRequest event) {
        log.info("Получен запрос GET /admin/events/{eventId}");
        return service.updateEventFilterAdminById(eventId, event);
    }

}

