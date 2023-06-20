package ru.practicum.service.repositories;

import ru.practicum.dto.StatDto;
import ru.practicum.dto.UserRequestDto;

import java.util.List;

public interface CustomStatsRepository {
    List<StatDto> findByParams(UserRequestDto request);
}
