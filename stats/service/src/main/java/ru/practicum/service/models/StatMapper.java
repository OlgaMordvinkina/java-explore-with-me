package ru.practicum.service.models;

import ru.practicum.dto.VisitDto;

public class StatMapper {
    public static Stat toStat(VisitDto visitDto) {
        return new Stat(
                visitDto.getId(),
                visitDto.getApp(),
                visitDto.getUri(),
                visitDto.getIp(),
                visitDto.getTimestamp()
        );
    }
}
