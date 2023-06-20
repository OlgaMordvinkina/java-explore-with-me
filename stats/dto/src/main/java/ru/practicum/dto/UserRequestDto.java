package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private static final String format = "yyyy-MM-dd HH:mm:ss";

    @NotNull
    @DateTimeFormat(pattern = format)
    private LocalDateTime start;
    @NotNull
    @DateTimeFormat(pattern = format)
    private LocalDateTime end;
    private Set<String> uris;
    private boolean unique;
}
