package ru.practicum.main.compilation.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.services.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class CompilationAdminController {
    private final CompilationService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompilationDto addCompilation(@Valid @RequestBody NewCompilationDto newCompilation) {
        log.info("Получен запрос POST /admin/compilations");
        return service.addCompilation(newCompilation);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Получен запрос DELETE /admin/compilations/{compId}");
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                            @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("Получен запрос PATCH /admin/compilations/{compId}");
        return service.updateCompilation(compId, updateCompilationRequest);
    }
}

