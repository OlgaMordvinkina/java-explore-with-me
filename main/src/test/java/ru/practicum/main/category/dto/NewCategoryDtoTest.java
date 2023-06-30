package ru.practicum.main.category.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class NewCategoryDtoTest {
    @Autowired
    private JacksonTester<NewCategoryDto> json;
    private final NewCategoryDto newCategoryDto = new NewCategoryDto("name");

    @Test
    void serializeTest() throws Exception {
        JsonContent<NewCategoryDto> result = json.write(newCategoryDto);

        assertThat(result).hasJsonPath("$.name");
    }
}
