package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {
    private Long id;
    private String title;
    private String annotation;
    private CategoryDto category;
    private boolean paid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private long confirmedRequests;
    private long views;

    public EventShortDto(Long id, String title, String annotation, CategoryDto category, boolean paid, LocalDateTime eventDate, UserShortDto initiator) {
        this.id = id;
        this.title = title;
        this.annotation = annotation;
        this.category = category;
        this.paid = paid;
        this.eventDate = eventDate;
        this.initiator = initiator;
    }
}
