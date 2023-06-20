package ru.practicum.service.validators;

import ru.practicum.dto.UserRequestDto;
import ru.practicum.service.exceptions.NotValidDataException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDateValidator implements ConstraintValidator<ValidDateConstraint, UserRequestDto> {
    private final String startDateValidationMessage = "Дата начала не может быть позже даты окончания";

    @Override
    public boolean isValid(UserRequestDto request, ConstraintValidatorContext cxt) {
        cxt.disableDefaultConstraintViolation();

        if (request.getStart() == null || request.getEnd() == null) {
            try {
                throw new NotValidDataException((request.getStart() == null) ? "start" : "end");
            } catch (NotValidDataException e) {
                throw new RuntimeException(e);
            }
        }

        boolean result = true;
        if (request.getStart().isAfter(request.getEnd())) {
            result = false;
            cxt.buildConstraintViolationWithTemplate(startDateValidationMessage)
                    .addPropertyNode("start")
                    .addConstraintViolation();
        }

        return result;
    }
}
