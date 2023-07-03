package ru.practicum.main.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDto {
    public static final String pattern = "yyyy-MM-dd HH:mm:ss";

    @NotBlank
    @Size(min = 3, max = 2000)
    private String text;
}
