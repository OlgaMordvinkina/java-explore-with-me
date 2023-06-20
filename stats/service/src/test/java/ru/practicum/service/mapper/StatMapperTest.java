package ru.practicum.service.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.dto.VisitDto;
import ru.practicum.service.models.Stat;
import ru.practicum.service.models.StatMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatMapperTest {
    private final LocalDateTime now = LocalDateTime.now();
    private VisitDto visit = new VisitDto(1L, "ewm-main-service", "/events/1", "100.000.00.1", now);
    private Stat stat = new Stat(1L, "ewm-main-service", "/events/1", "100.000.00.1", now);

    @Test
    void toStatMappingTest() {
        Stat expectedStat = StatMapper.toStat(visit);
        assertEquals(stat.toString(), expectedStat.toString());
    }
}
