package ru.practicum.service.services;

import ru.practicum.dto.StatDto;
import ru.practicum.dto.VisitDto;
import ru.practicum.dto.UserRequestDto;

import java.util.List;

public interface StatsService {
    void createStat(VisitDto visitDto);

    List<StatDto> getStats(UserRequestDto request);
}
