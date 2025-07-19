package com.cct.schoolleavers.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation for value validation
 * 
 * Validates that the value is within acceptable range and format
 * 
 * @author CCT Student
 * @version 1.0
 */
@Documented
@Constraint(validatedBy = ValidValueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidValue {
    
    String message() default "Value must be between 0.0 and 999.99";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
} 