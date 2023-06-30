package ru.practicum.main.event.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateConstraint {
    String message() default "должно содержать дату, которая еще не наступила";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
