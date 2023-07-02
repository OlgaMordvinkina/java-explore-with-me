package ru.practicum.service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.UserRequestDto;
import ru.practicum.dto.VisitDto;
import ru.practicum.service.services.StatsService;
import ru.practicum.service.validators.ValidDateConstraint;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatsController {
    private final StatsService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public void createStat(@RequestBody VisitDto visitDto) {
        log.info("Получен запрос POST /hit");
        service.createStat(visitDto);
    }

    @GetMapping("/stats")
    public List<StatDto> getStats(@Valid @ValidDateConstraint UserRequestDto request) {
        log.info("Получен запрос GET /stats");
        return service.getStats(request);
    }

    @GetMapping("/views")
    public Map<Long, Long> findViews(@RequestParam String eventIds) {
        log.info("Получен запрос GET /views");
        return service.findViews(List.of(eventIds.split(",")));
    }
}
