package ru.practicum.main.event.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class ValidDateUpdateValidator implements ConstraintValidator<ValidDateUpdateConstraint, LocalDateTime> {
    private final String startDateValidationMessage = "Время начала не может быть раньше, чем через час от текущего момента";

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext cxt) {
        cxt.disableDefaultConstraintViolation();

        boolean result = true;
        if (eventDate != null && eventDate.isBefore(LocalDateTime.now().plusHours(1))) {
            result = false;
            cxt.buildConstraintViolationWithTemplate(startDateValidationMessage)
                    .addConstraintViolation();
        }

        return result;
    }
}
