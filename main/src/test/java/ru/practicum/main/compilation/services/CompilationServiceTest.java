package ru.practicum.main.compilation.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.exceptions.NotFoundCompilationException;
import ru.practicum.main.compilation.models.Compilation;
import ru.practicum.main.compilation.repositories.CompilationRepository;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.event.repositories.EventRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompilationServiceTest {
    @Mock
    private CompilationRepository compilationRepository;
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private CompilationServiceImpl service;
    public static final long id = 1L;
    public static final String title = "title";
    public static final boolean pinned = true;
    public static final List<Long> eventsIds = Collections.singletonList(1L);
    private static final List<Event> events = Collections.singletonList(new Event());
    private final Compilation compilation = new Compilation(1L, title, pinned, new ArrayList<>());
    private final CompilationDto compilationDto = new CompilationDto(id, title, pinned, new ArrayList<>());
    private final NewCompilationDto newCompilationDto = new NewCompilationDto(title, pinned, eventsIds);
    private final List<CompilationDto> compilations = Collections.singletonList(compilationDto);
    private final UpdateCompilationRequest request = new UpdateCompilationRequest(title, true, eventsIds);

    @Test
    void updateCompilation_returnNotFoundCompilationExceptionTest() {
        assertThrows(NotFoundCompilationException.class, () -> service.updateCompilation(id, request));
    }

    @Test
    void deleteCompilationTest() {
        when(compilationRepository.findById(anyLong())).thenReturn(Optional.of(compilation));

        service.deleteCompilation(1L);
        verify(compilationRepository).deleteById(anyLong());
    }

    @Test
    void deleteCompilation_returnNotFoundCompilationExceptionTest() {
        assertThrows(NotFoundCompilationException.class, () -> service.deleteCompilation(id));
    }

    @Test
    void getCompilationsTest() {
        when(compilationRepository.findAllByPinned(anyBoolean(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(compilation)));

        List<CompilationDto> expectedCompilations = service.getCompilations(pinned, 0, 10);

        assertEquals(expectedCompilations, compilations);

        assertNotNull(expectedCompilations);
        assertFalse(expectedCompilations.isEmpty());
    }

    @Test
    void getCompilationTest() {
        when(compilationRepository.findById(anyLong())).thenReturn(Optional.of(compilation));

        CompilationDto expectedCompilation = service.getCompilationById(id);

        assertEquals(compilationDto, expectedCompilation);
    }

    @Test
    void getCompilation_returnNotFoundCompilationExceptionTest() {
        assertThrows(NotFoundCompilationException.class, () -> service.updateCompilation(id, request));
    }

}
