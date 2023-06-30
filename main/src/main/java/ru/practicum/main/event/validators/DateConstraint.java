package ru.practicum.main.event.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDate.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
    String message() default "Дата старта не может быть позже даты окончания";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
