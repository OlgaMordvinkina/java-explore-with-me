package ru.practicum.service.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.MapBindingResult;
import ru.practicum.service.exceptions.NotValidDataException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestResponseEntityExceptionHandlerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestResponseEntityExceptionHandler handler = new RestResponseEntityExceptionHandler(objectMapper);
    private final int badRequest = 400;

    @Test
    public void notFoundItemExceptionTest() throws JsonProcessingException {
        ResponseEntity<Object> response = handler.handleConflict(new NotValidDataException(""));

        assertNotNull(response);
        assertEquals(response.getStatusCode().value(), badRequest);
    }

    @Test
    public void notFoundUserExceptionTest() throws JsonProcessingException {
        ResponseEntity<Object> response = handler.handleConflict(new BindException(new MapBindingResult(new HashMap<>(), "")));

        assertNotNull(response);
        assertEquals(response.getStatusCode().value(), badRequest);
    }
}
