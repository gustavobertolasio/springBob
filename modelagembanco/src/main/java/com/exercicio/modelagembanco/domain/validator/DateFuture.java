package com.exercicio.modelagembanco.domain.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = DateFutureValidator.class)
@Documented
public @interface DateFuture {

    String message() default "As datas precisam ser iguais hoje ou futuras";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

} 