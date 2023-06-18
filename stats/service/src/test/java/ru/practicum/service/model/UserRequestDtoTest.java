package ru.practicum.service.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.dto.UserRequestDto;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserRequestDtoTest {
    @Autowired
    private JacksonTester<UserRequestDto> json;
    private final LocalDateTime now = LocalDateTime.now();
    private UserRequestDto requestDto = new UserRequestDto(now, now, Collections.singleton("/events/1"), true);

    @Test
    void serializeTest() throws Exception {
        JsonContent<UserRequestDto> result = json.write(requestDto);

        assertThat(result).hasJsonPath("$.start");
        assertThat(result).hasJsonPath("$.end");
        assertThat(result).hasJsonPath("$.uris");
        assertThat(result).hasJsonPath("$.unique");
    }
}
