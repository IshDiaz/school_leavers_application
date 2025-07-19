package com.cct.schoolleavers.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for ValidStatisticCode annotation
 * 
 * Validates that the statistic code contains only letters and numbers
 * 
 * @author CCT Student
 * @version 1.0
 */
public class ValidStatisticCodeValidator implements ConstraintValidator<ValidStatisticCode, String> {
    
    @Override
    public void initialize(ValidStatisticCode constraintAnnotation) {
        // No initialization needed
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotBlank handle null/empty validation
        }
        
        // Check if the value contains only letters and numbers
        return value.matches("^[a-zA-Z0-9]+$");
    }
} 