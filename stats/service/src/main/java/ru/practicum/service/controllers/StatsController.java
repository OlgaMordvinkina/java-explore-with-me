package ru.practicum.service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.StatDto;
import ru.practicum.model.VisitDto;
import ru.practicum.service.models.UserRequestDto;
import ru.practicum.service.services.StatsService;

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
    public List<StatDto> getStats(@Valid UserRequestDto request) {
        List<StatDto> stats = service.getStats(request);
        log.info("Получен запрос GET /stats");
        return stats;
    }
}
