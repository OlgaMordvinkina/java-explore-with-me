package ru.practicum.main.event.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.event.dto.EventFilterDto;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.services.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class EventPublicController {
    private final EventService service;

    @GetMapping
    public List<EventShortDto> getEventsFilter(@Valid EventFilterDto filter,
                                               HttpServletRequest request) {
        log.info("Получен запрос GET /events");
        return service.getEventsFilter(filter, request);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventFilterById(@PathVariable Long id,
                                           HttpServletRequest request) {
        log.info("Получен запрос PATCH /events/{id}");
        return service.getEventFilterById(id, request);
    }

}

