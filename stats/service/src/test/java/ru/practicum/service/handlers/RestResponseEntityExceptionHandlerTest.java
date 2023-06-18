package ru.practicum.service.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.practicum.service.exceptions.NotValidDataException;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestResponseEntityExceptionHandlerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestResponseEntityExceptionHandler handler = new RestResponseEntityExceptionHandler(objectMapper);
    private final int badRequest = 400;

    @Test
    public void notValidDataExceptionTest() throws JsonProcessingException {
        ResponseEntity<Object> response = handler.handleConflict(new NotValidDataException(""));

        assertNotNull(response);
        assertEquals(response.getStatusCode().value(), badRequest);
    }

    @Test
    public void constraintViolationExceptionTest() throws JsonProcessingException {
        ResponseEntity<Object> response = handler.handleConflict(new ConstraintViolationException(new HashSet<>()));

        assertNotNull(response);
        assertEquals(response.getStatusCode().value(), badRequest);
    }
}
