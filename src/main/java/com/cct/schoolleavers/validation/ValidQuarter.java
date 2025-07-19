package com.cct.schoolleavers.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation for quarter format
 * 
 * Validates that the quarter is in the format Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY
 * 
 * @author CCT Student
 * @version 1.0
 */
@Documented
@Constraint(validatedBy = ValidQuarterValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidQuarter {
    
    String message() default "Quarter must be in format Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
} 