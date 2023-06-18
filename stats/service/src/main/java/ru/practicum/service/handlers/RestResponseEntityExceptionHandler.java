package ru.practicum.service.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.service.exceptions.NotValidDataException;
import ru.practicum.service.exceptions.model.ErrorResponse;
import ru.practicum.service.exceptions.model.ErrorsDescription;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestResponseEntityExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConflict(ConstraintViolationException ex) throws JsonProcessingException {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .errors(
                        List.of(ErrorsDescription.builder()
                                .fieldName("start")
                                .message((ex.getMessage()))
                                .build())
                ).build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(response));
    }

    @ExceptionHandler(NotValidDataException.class)
    protected ResponseEntity<Object> handleConflict(NotValidDataException ex) throws JsonProcessingException {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .errors(
                        List.of(ErrorsDescription.builder()
                                .fieldName(ex.getField())
                                .message(ex.getMessage())
                                .build())
                ).build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(response));
    }
}
