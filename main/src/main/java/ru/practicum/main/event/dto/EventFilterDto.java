package ru.practicum.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.main.event.enums.StateSort;
import ru.practicum.main.event.validators.DateConstraint;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DateConstraint
public class EventFilterDto {
    private static final String format = "yyyy-MM-dd HH:mm:ss";

    private String text;
    private List<Long> categories;
    private Boolean paid;
    @DateTimeFormat(pattern = format)
    private LocalDateTime rangeStart;
    @DateTimeFormat(pattern = format)
    private LocalDateTime rangeEnd;
    private boolean onlyAvailable;
    private StateSort sort;
    @PositiveOrZero
    private Integer from = 0;
    @Positive
    private Integer size = 10;
}

