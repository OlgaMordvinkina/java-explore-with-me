package ru.practicum.service.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDateValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateConstraint {
    String message() default "Дата начала не может быть позже даты окончания";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
