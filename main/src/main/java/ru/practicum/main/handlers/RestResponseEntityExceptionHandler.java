package ru.practicum.main.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.category.exceptions.NotFoundCategoryException;
import ru.practicum.main.comments.exceptions.NotFoundCommentException;
import ru.practicum.main.compilation.exceptions.NotFoundCompilationException;
import ru.practicum.main.event.exceptions.NotFoundEventException;
import ru.practicum.main.exceptions.AccessException;
import ru.practicum.main.exceptions.LimitException;
import ru.practicum.main.exceptions.NotAvailableException;
import ru.practicum.main.handlers.model.ApiError;
import ru.practicum.main.handlers.model.Status;
import ru.practicum.main.user.exceptions.NotFoundUserException;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestResponseEntityExceptionHandler {
    public static final String REQUIRED_OBJECT_WAS_NOT_FOUND = "The required object was not found.";
    public static final String FOR_THE_REQUESTED_OPERATION = "For the requested operation the conditions are not met.";
    private static final String format = "yyyy-MM-dd HH:mm:ss";
    private final ObjectMapper objectMapper;

    @ExceptionHandler(NotFoundCategoryException.class)
    protected ResponseEntity<Object> handleConflict(NotFoundCategoryException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason(REQUIRED_OBJECT_WAS_NOT_FOUND)
                .message(getMessage(ex.getField(), ex.getId()))
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.NOT_FOUND, response);
    }

    @ExceptionHandler(NotFoundUserException.class)
    protected ResponseEntity<Object> handleConflict(NotFoundUserException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason(REQUIRED_OBJECT_WAS_NOT_FOUND)
                .message(getMessage(ex.getField(), ex.getId()))
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.NOT_FOUND, response);
    }

    @ExceptionHandler(NotFoundCompilationException.class)
    protected ResponseEntity<Object> handleConflict(NotFoundCompilationException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason(REQUIRED_OBJECT_WAS_NOT_FOUND)
                .message(getMessage(ex.getField(), ex.getId()))
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.NOT_FOUND, response);
    }

    @ExceptionHandler(NotFoundEventException.class)
    protected ResponseEntity<Object> handleConflict(NotFoundEventException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason(REQUIRED_OBJECT_WAS_NOT_FOUND)
                .message(getMessage(ex.getField(), ex.getId()))
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.NOT_FOUND, response);
    }

    @ExceptionHandler(NotFoundCommentException.class)
    protected ResponseEntity<Object> handleConflict(NotFoundCommentException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason(REQUIRED_OBJECT_WAS_NOT_FOUND)
                .message(getMessage(ex.getField(), ex.getId()))
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.NOT_FOUND, response);
    }

    @ExceptionHandler(LimitException.class)
    protected ResponseEntity<Object> handleConflict(LimitException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(HttpStatus.CONFLICT.name())
                .reason(FOR_THE_REQUESTED_OPERATION)
                .message("The participant limit has been reached")
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.CONFLICT, response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleConflict(HttpMessageNotReadableException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(HttpStatus.CONFLICT.name())
                .reason(FOR_THE_REQUESTED_OPERATION)
                .message("The participant limit has been reached")
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.CONFLICT, response);
    }

    @ExceptionHandler(AccessException.class)
    protected ResponseEntity<Object> handleConflict(AccessException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason(REQUIRED_OBJECT_WAS_NOT_FOUND)
                .message(getMessage(ex.getField(), ex.getId()))
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.NOT_FOUND, response);
    }

    @ExceptionHandler(NotAvailableException.class)
    protected ResponseEntity<Object> handleConflict(NotAvailableException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(HttpStatus.FORBIDDEN.name())
                .reason(FOR_THE_REQUESTED_OPERATION)
                .message("Cannot publish the event because it's not in the right state: PUBLISHED")
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.CONFLICT, response);
    }

    @ExceptionHandler({NumberFormatException.class})
    protected ResponseEntity<Object> handleConflict(NumberFormatException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("Incorrectly made request.")
                .message(ex.getMessage())
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.BAD_REQUEST, response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConflict(ConstraintViolationException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(Status.CONFLICT.name())
                .reason("Integrity constraint has been violated.")
                .message("could not execute statement; SQL [n/a]; constraint [uq_category_name]; " +
                        "nested exception is org.hibernate.exception.ConstraintViolationException: " +
                        "could not execute statement")
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.CONFLICT, response);
    }

    @ExceptionHandler(PersistenceException.class)
    protected ResponseEntity<Object> handleConflict(PersistenceException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(Status.CONFLICT.name())
                .reason("Integrity constraint has been violated.")
                .message(ex.getMessage() + "; SQL [n/a]; constraint [uq_category_name]; " +
                        "nested exception is org.hibernate.exception.ConstraintViolationException: " +
                        "could not execute statement")
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.CONFLICT, response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleConflict(MethodArgumentNotValidException ex) throws JsonProcessingException {
        ApiError response = ApiError.builder()
                .status(Status.FORBIDDEN.name())
                .reason(FOR_THE_REQUESTED_OPERATION)
                .message("Field: " + Objects.requireNonNull(ex.getFieldError()).getField() +
                        ". Error: " + Objects.requireNonNull(ex.getFieldError()).getDefaultMessage() + ". " +
                        "Value: " + Objects.requireNonNull(ex.getFieldError()).getRejectedValue())
                .timestamp(getLocalDate())
                .build();

        return getResponseEntity(HttpStatus.BAD_REQUEST, response);
    }

    private ResponseEntity<Object> getResponseEntity(HttpStatus status, ApiError response) throws JsonProcessingException {
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(response));
    }

    private String getMessage(String field, Long id) {
        return field + " with id=" + id + " was not found.";
    }

    private String getLocalDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }
}
