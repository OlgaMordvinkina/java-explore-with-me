package ru.practicum.main.category.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class CategoryDtoTest {
    @Autowired
    private JacksonTester<CategoryDto> json;
    private final CategoryDto categoryDto = new CategoryDto(1L, "name");

    @Test
    void serializeTest() throws Exception {
        JsonContent<CategoryDto> result = json.write(categoryDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");
    }
}
