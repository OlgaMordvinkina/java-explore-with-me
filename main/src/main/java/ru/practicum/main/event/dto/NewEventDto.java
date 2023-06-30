package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.main.event.models.Location;
import ru.practicum.main.event.validators.ValidDateConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    @NotNull
    @Size(min = 3, max = 120)
    private String title;
    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    private boolean paid;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ValidDateConstraint
    private LocalDateTime eventDate;
    @NotNull
    @Size(min = 20, max = 7000)
    private String description;
    private int participantLimit = 0;
    @NotNull
    private Location location;
    private boolean requestModeration = true;
}
