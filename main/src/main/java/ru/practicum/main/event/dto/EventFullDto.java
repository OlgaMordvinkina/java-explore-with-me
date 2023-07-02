package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.event.enums.State;
import ru.practicum.main.event.models.Location;
import ru.practicum.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    private static final String format = "yyyy-MM-dd HH:mm:ss";

    private Long id;
    private String title;
    private String annotation;
    private CategoryDto category;
    private boolean paid;
    @JsonFormat(pattern = format)
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private String description;
    private int participantLimit;
    private boolean requestModeration;
    private State state;
    @JsonFormat(pattern = format)
    private LocalDateTime createdOn;
    private Location location;
    @JsonFormat(pattern = format)
    private LocalDateTime publishedOn;
    private long confirmedRequests;
    private long views;

    public EventFullDto(Long id, String title, String annotation, CategoryDto category, boolean paid, LocalDateTime eventDate, UserShortDto initiator, String description, int participantLimit, boolean requestModeration, State state, LocalDateTime createdOn, Location location, LocalDateTime publishedOn) {
        this.id = id;
        this.title = title;
        this.annotation = annotation;
        this.category = category;
        this.paid = paid;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.description = description;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.state = state;
        this.createdOn = createdOn;
        this.location = location;
        this.publishedOn = publishedOn;
    }
}
