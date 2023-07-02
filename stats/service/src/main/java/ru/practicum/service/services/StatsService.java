package ru.practicum.service.services;

import ru.practicum.dto.StatDto;
import ru.practicum.dto.UserRequestDto;
import ru.practicum.dto.VisitDto;

import java.util.List;
import java.util.Map;

public interface StatsService {
    void createStat(VisitDto visitDto);

    List<StatDto> getStats(UserRequestDto request);

    Map<Long, Long> findViews(List<String> eventIds);
}
