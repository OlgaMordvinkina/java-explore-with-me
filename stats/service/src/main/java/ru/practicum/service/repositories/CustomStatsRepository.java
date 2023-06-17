package ru.practicum.service.repositories;

import ru.practicum.model.StatDto;
import ru.practicum.service.models.UserRequestDto;

import java.util.List;

public interface CustomStatsRepository {
    List<StatDto> findByParams(UserRequestDto request);
}
