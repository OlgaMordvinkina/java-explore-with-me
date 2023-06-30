package ru.practicum.service.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.UserRequestDto;
import ru.practicum.dto.VisitDto;
import ru.practicum.service.repositories.StatsRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatServiceTest {
    @Mock
    private StatsRepository repository;
    @InjectMocks
    private StatsServiceImpl service;
    private final LocalDateTime now = LocalDateTime.now();
    private VisitDto visit = new VisitDto(1L, "ewm-main-service", "/events/1", "100.000.00.1", now);
    private UserRequestDto request = new UserRequestDto(now.minusDays(2), now, Collections.singleton("/events/1"), false);
    private StatDto statDto = new StatDto("app-main-service", "/events/1", 5);
    private List<StatDto> stats = Collections.singletonList(statDto);

    @Test
    void createStatTest() {
        service.createStat(visit);
        verify(repository).save(any());
    }

    @Test
    void getStatsTest() {
        when(service.getStats(any())).thenReturn(stats);
        List<StatDto> expectedStats = service.getStats(request);
        assertEquals(stats, expectedStats);
    }
}
