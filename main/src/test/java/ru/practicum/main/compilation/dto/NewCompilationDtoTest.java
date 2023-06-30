package ru.practicum.main.compilation.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class NewCompilationDtoTest {
    @Autowired
    private JacksonTester<NewCompilationDto> json;
    private final NewCompilationDto newCategoryDto = new NewCompilationDto("title", true, Collections.singletonList(1L));

    @Test
    void serializeTest() throws Exception {
        JsonContent<NewCompilationDto> result = json.write(newCategoryDto);

        assertThat(result).hasJsonPath("$.title");
        assertThat(result).hasJsonPath("$.pinned");
        assertThat(result).hasJsonPath("$.events");
    }
}
