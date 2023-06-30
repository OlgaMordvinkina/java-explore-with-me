package ru.practicum.main.request.mapper;

import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.models.Request;

public class RequestMapper {

    public static ParticipationRequestDto toRequestDto(Request request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getRequester().getId(),
                request.getEvent().getId(),
                request.getStatus(),
                request.getCreated()
        );
    }

}
