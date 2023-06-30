package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main.event.enums.StateAction;
import ru.practicum.main.event.models.Location;
import ru.practicum.main.event.validators.ValidDateConstraint;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequest {
    @Size(min = 3, max = 120)
    private String title;
    @Size(min = 20, max = 2000)
    private String annotation;
    private Long category;
    private Boolean paid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ValidDateConstraint
    private LocalDateTime eventDate;
    @Size(min = 20, max = 7000)
    private String description;
    private Integer participantLimit;
    private Location location;
    private Boolean requestModeration;
    private StateAction stateAction;
}
