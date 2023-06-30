package ru.practicum.service.repositories;

import ru.practicum.dto.StatDto;
import ru.practicum.dto.UserRequestDto;
import ru.practicum.service.models.View;

import java.util.List;

public interface CustomStatsRepository {
    List<StatDto> findByParams(UserRequestDto request);

    List<View> getViews(List<String> ids);
}
