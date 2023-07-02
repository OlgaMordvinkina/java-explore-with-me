package ru.practicum.main.event.mapper;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.models.Category;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.enums.State;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.event.models.EventShort;
import ru.practicum.main.event.models.Location;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {
    private static final String format = "yyyy-MM-dd HH:mm:ss";

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.isPaid(),
                event.getEventDate(),
                UserMapper.toUserShortDto(event.getInitiator())
        );
    }

    public static EventShortDto toEventShortDto(EventShort event, long confirmedRequest, long view) {
        return new EventShortDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getPaid(),
                event.getEventDate(),
                UserMapper.toUserShortDto(event.getInitiator()),
                confirmedRequest,
                view
        );
    }

    public static Event toEvent(NewEventDto event, Category category, User user, Location location) {
        LocalDateTime createdOn = getLocalDate();
        return new Event(
                null,
                event.getTitle(),
                event.getAnnotation(),
                category,
                event.isPaid(),
                event.getEventDate(),
                user,
                event.getDescription(),
                event.getParticipantLimit(),
                createdOn,
                location,
                event.isRequestModeration(),
                null,
                State.PENDING
        );
    }

    public static EventFullDto toEventFullDto(Event event, long confirmedRequest, long view) {
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(event.getCategory());
        UserShortDto userShortDto = UserMapper.toUserShortDto(event.getInitiator());
        return new EventFullDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                categoryDto,
                event.isPaid(),
                event.getEventDate(),
                userShortDto,
                event.getDescription(),
                event.getParticipantLimit(),
                event.isRequestModeration(),
                event.getState(),
                event.getCreatedOn(),
                event.getLocation(),
                event.getPublishedOn(),
                confirmedRequest,
                view
        );
    }

    public static EventFullDto toEventFullDto(Event event) {
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(event.getCategory());
        UserShortDto userShortDto = UserMapper.toUserShortDto(event.getInitiator());
        return new EventFullDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                categoryDto,
                event.isPaid(),
                event.getEventDate(),
                userShortDto,
                event.getDescription(),
                event.getParticipantLimit(),
                event.isRequestModeration(),
                event.getState(),
                event.getCreatedOn(),
                event.getLocation(),
                event.getPublishedOn()
        );
    }

    private static LocalDateTime getLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
    }
}
