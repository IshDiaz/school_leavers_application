package com.cct.schoolleavers.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Validator implementation for ValidValue annotation
 * 
 * Validates that the value is within acceptable range and format
 * 
 * @author CCT Student
 * @version 1.0
 */
public class ValidValueValidator implements ConstraintValidator<ValidValue, BigDecimal> {
    
    @Override
    public void initialize(ValidValue constraintAnnotation) {
        // No initialization needed
    }
    
    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null validation
        }
        
        // Check if value is within range (0.0 to 999.99)
        BigDecimal min = new BigDecimal("0.0");
        BigDecimal max = new BigDecimal("999.99");
        
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }
} 