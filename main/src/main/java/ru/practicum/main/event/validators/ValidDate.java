package ru.practicum.main.event.validators;

import ru.practicum.main.event.dto.EventFilterDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDate implements ConstraintValidator<DateConstraint, EventFilterDto> {
    private final String startDateValidationMessage = "Дата старта не может быть позже даты окончания";

    @Override
    public boolean isValid(EventFilterDto request, ConstraintValidatorContext cxt) {
        cxt.disableDefaultConstraintViolation();

        boolean result = true;

        if (request.getRangeStart() == null) {
            return result;
        }

        if (request.getRangeStart().isAfter(request.getRangeEnd())) {
            result = false;
            cxt.buildConstraintViolationWithTemplate(startDateValidationMessage)
                    .addPropertyNode("rangeStart")
                    .addConstraintViolation();
        }

        return result;
    }
}
