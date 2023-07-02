package ru.practicum.main.event.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class ValidDateValidator implements ConstraintValidator<ValidDateConstraint, LocalDateTime> {
    private final String startDateValidationMessage = "должно содержать дату, которая еще не наступила";

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext cxt) {
        cxt.disableDefaultConstraintViolation();

        boolean result = true;
        if (eventDate != null && eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            result = false;
            cxt.buildConstraintViolationWithTemplate(startDateValidationMessage)
                    .addConstraintViolation();
        }

        return result;
    }
}
