package com.example.validation.annotation;

import com.example.validation.validator.PHoneNumberValidator;
import com.example.validation.validator.YearMonthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {YearMonthValidator.class}) // 해당 검증을 하겠다
@Target({ElementType.FIELD}) // 어디에?
@Retention(RetentionPolicy.RUNTIME) // 언제 실행시킬것이냐?
@NotBlank
public @interface YearMonth {
    String message()default "year month 양식에 맞지 않습니다. ex) 20230101";
    String pattern() default "yyyyMMdd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
