package ru.practicum.main.event.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.main.category.models.Category;
import ru.practicum.main.user.models.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@AllArgsConstructor
public class EventShort {
    private Long id;
    private String title;
    private String annotation;
    private Category category;
    private Boolean paid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private User initiator;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
}
