package ru.practicum.service.services;

import ru.practicum.model.StatDto;
import ru.practicum.model.VisitDto;
import ru.practicum.service.models.UserRequestDto;

import java.util.List;

public interface StatsService {
    void createStat(VisitDto visitDto);

    List<StatDto> getStats(UserRequestDto request);
}
