package com.cct.schoolleavers.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

/**
 * Validator implementation for ValidQuarter annotation
 * 
 * Validates that the quarter is in the format Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY
 * and that the year is reasonable (between 1900 and current year + 10)
 * 
 * @author CCT Student
 * @version 1.0
 */
public class ValidQuarterValidator implements ConstraintValidator<ValidQuarter, String> {
    
    @Override
    public void initialize(ValidQuarter constraintAnnotation) {
        // No initialization needed
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotBlank handle null/empty validation
        }
        
        // Check if the value matches the quarter format
        if (!value.matches("^Q[1-4]\\d{4}$")) {
            return false;
        }
        
        // Extract year and validate it
        try {
            int year = Integer.parseInt(value.substring(2));
            int currentYear = Year.now().getValue();
            
            // Year should be between 1900 and current year + 10
            return year >= 1900 && year <= currentYear + 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }
} 