package ru.practicum.main.compilation.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.main.event.dto.EventShortDto;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class CompilationDtoTest {
    @Autowired
    private JacksonTester<CompilationDto> json;
    private final CompilationDto compilationDto = new CompilationDto(1L, "title", true, Collections.singletonList(new EventShortDto()));

    @Test
    void serializeTest() throws Exception {
        JsonContent<CompilationDto> result = json.write(compilationDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.title");
        assertThat(result).hasJsonPath("$.pinned");
        assertThat(result).hasJsonPath("$.events");
    }
}
