package ru.practicum.service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.VisitDto;
import ru.practicum.dto.UserRequestDto;
import ru.practicum.service.services.StatsService;
import ru.practicum.service.validators.ValidDateConstraint;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatsController {
    private final StatsService service;

    @PostMapping("/hit")
    public void createStat(@RequestBody VisitDto visitDto) {
        log.info("Получен запрос POST /hit");
        service.createStat(visitDto);
    }

    @GetMapping("/stats")
    public List<StatDto> getStats(@Valid @ValidDateConstraint UserRequestDto request) {
        List<StatDto> stats = service.getStats(request);
        log.info("Получен запрос GET /stats");
        return stats;
    }
}
