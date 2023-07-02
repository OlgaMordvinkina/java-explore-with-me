package ru.practicum.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    @Size(min = 1, max = 50)
    @NotBlank
    private String title;
    private boolean pinned;
    @Nullable
    private List<Long> events;
}
