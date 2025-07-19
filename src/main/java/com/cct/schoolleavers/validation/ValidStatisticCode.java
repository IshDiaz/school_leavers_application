package com.cct.schoolleavers.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation for statistic code
 * 
 * Validates that the statistic code contains only letters and numbers
 * 
 * @author CCT Student
 * @version 1.0
 */
@Documented
@Constraint(validatedBy = ValidStatisticCodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStatisticCode {
    
    String message() default "Statistic code must contain only letters and numbers";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
} 