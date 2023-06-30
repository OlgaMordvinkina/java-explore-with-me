package ru.practicum.main.event.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDateUpdateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateUpdateConstraint {
    String message() default "Время начала не может быть раньше, чем через час от текущего момента";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
