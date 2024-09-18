package com.example.validation.validator;

import com.example.validation.annotation.PhoneNumber;
import com.example.validation.annotation.YearMonth;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class YearMonthValidator implements ConstraintValidator<YearMonth, String> {
    private String pattern;

    @Override
    public void initialize(YearMonth constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // "2023-01-01T13:00:00" yyyy-MM-ddTHH:mm:ss
        // "20230101"
        // size = 6

        var reValue = value+"01"; // 유효한 날짜 검증 위해서..
        var rePattern = pattern+"dd"; // 이것도...

        try {
            LocalDate date = LocalDate.parse(reValue, DateTimeFormatter.ofPattern(rePattern)); // 유효한 날짜
            System.out.println(date);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
