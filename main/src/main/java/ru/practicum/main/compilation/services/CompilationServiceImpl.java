package ru.practicum.main.compilation.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.exceptions.NotFoundCompilationException;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.models.Compilation;
import ru.practicum.main.compilation.repositories.CompilationRepository;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.event.repositories.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        List<CompilationDto> compilations = compilationRepository.findAllByPinned(pinned, PageRequest.of(from / size, size)).stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
        log.info("Получены Compilations: {}", compilations);
        return compilations;
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = existById(compId);
        log.info("Получены Compilations: {}", compilation);
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilation) {
        List<Event> events = newCompilation.getEvents() != null && !newCompilation.getEvents().isEmpty()
                ? existEventsByIds(newCompilation.getEvents())
                : List.of();
        Compilation compilation = compilationRepository.save(CompilationMapper.toCompilation(newCompilation, events));
        log.info("Добавлен Compilation: {}", compilation);
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public void deleteCompilation(Long compId) {
        Compilation compilation = existById(compId);
        log.info("Удален Compilation: {}", compilation);
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest request) {
        Compilation compilation = existById(compId);
        if (request.getTitle() != null) compilation.setTitle(compilation.getTitle());
        if (request.getEvents() != null) compilation.setEvents(existEventsByIds(request.getEvents()));
        if (request.getPinned() != null) compilation.setPinned(request.getPinned());
        log.info("Обновлен Compilation: {}", compilation);
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    private Compilation existById(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() -> new NotFoundCompilationException(compId));
    }

    private List<Event> existEventsByIds(List<Long> ids) {
        return (ids != null && !ids.isEmpty() ? eventRepository.findAllById(ids) : null);
    }
}
