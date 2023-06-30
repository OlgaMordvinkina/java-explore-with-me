package ru.practicum.main.handlers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiError {
    private String status;
    private String reason;
    private String message;
    private String timestamp;
    @JsonIgnore
    private String errors;
}
