package ru.practicum.main.compilation.mapper;

import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.models.Compilation;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.models.Event;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.isPinned(),
                compilation.getEvents() != null ?
                        compilation.getEvents().stream()
                                .map(EventMapper::toEventShortDto)
                                .collect(Collectors.toList()) :
                        List.of()
        );
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(
                null,
                newCompilationDto.getTitle(),
                newCompilationDto.isPinned(),
                events
        );
    }

    public static Compilation toCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest, List<Event> events) {
        return new Compilation(
                compId,
                updateCompilationRequest.getTitle(),
                updateCompilationRequest.getPinned(),
                events
        );
    }
}
