package ru.practicum.main.compilation.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.services.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class CompilationPublicController {
    private final CompilationService service;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(defaultValue = "false") boolean pinned,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        log.info("Получен запрос GET /compilations");
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        log.info("Получен запрос GET /compilations/{compId}");
        return service.getCompilationById(compId);
    }
}

