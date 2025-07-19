package com.cct.schoolleavers.services;

import com.cct.schoolleavers.dto.SchoolLeaverDto;
import com.cct.schoolleavers.dto.SearchDto;
import com.cct.schoolleavers.dto.UserDto;
import com.cct.schoolleavers.util.Constants;
import com.cct.schoolleavers.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Validation Service
 * 
 * This service provides comprehensive form validation functionality
 * for all forms in the application.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Service
public class ValidationService {
    
    private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);
    
    /**
     * Validate school leaver form data
     * 
     * @param schoolLeaverDto the school leaver data to validate
     * @param bindingResult the binding result to add errors to
     * @return true if valid, false otherwise
     */
    public boolean validateSchoolLeaverForm(SchoolLeaverDto schoolLeaverDto, BindingResult bindingResult) {
        logger.info("Validating school leaver form data");
        
        boolean isValid = true;
        
        // Validate statistic code
        if (!ValidationUtil.isNotEmpty(schoolLeaverDto.getStatisticCode())) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "statisticCode", 
                "Statistic code is required"));
            isValid = false;
        } else if (!ValidationUtil.isValidLength(schoolLeaverDto.getStatisticCode(), 1, Constants.MAX_STATISTIC_CODE_LENGTH)) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "statisticCode", 
                "Statistic code must be between 1 and " + Constants.MAX_STATISTIC_CODE_LENGTH + " characters"));
            isValid = false;
        } else if (!ValidationUtil.isValidStatisticCode(schoolLeaverDto.getStatisticCode())) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "statisticCode", 
                "Statistic code must contain only letters and numbers"));
            isValid = false;
        }
        
        // Validate statistic label
        if (!ValidationUtil.isNotEmpty(schoolLeaverDto.getStatisticLabel())) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "statisticLabel", 
                "Statistic label is required"));
            isValid = false;
        } else if (!ValidationUtil.isValidLength(schoolLeaverDto.getStatisticLabel(), 1, Constants.MAX_STATISTIC_LABEL_LENGTH)) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "statisticLabel", 
                "Statistic label must be between 1 and " + Constants.MAX_STATISTIC_LABEL_LENGTH + " characters"));
            isValid = false;
        } else if (!schoolLeaverDto.getStatisticLabel().matches("^[a-zA-Z0-9\\s\\-\\(\\)\\,\\.]+$")) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "statisticLabel", 
                "Statistic label can only contain letters, numbers, spaces, hyphens, parentheses, and commas"));
            isValid = false;
        }
        
        // Validate quarter
        if (!ValidationUtil.isNotEmpty(schoolLeaverDto.getQuarter())) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "quarter", 
                "Quarter is required"));
            isValid = false;
        } else if (!ValidationUtil.isValidQuarter(schoolLeaverDto.getQuarter())) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "quarter", 
                "Quarter must be in format Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY"));
            isValid = false;
        }
        
        // Validate sex
        if (!ValidationUtil.isNotEmpty(schoolLeaverDto.getSex())) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "sex", 
                "Sex is required"));
            isValid = false;
        } else if (!ValidationUtil.isValidLength(schoolLeaverDto.getSex(), 1, Constants.MAX_SEX_LENGTH)) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "sex", 
                "Sex must be between 1 and " + Constants.MAX_SEX_LENGTH + " characters"));
            isValid = false;
        } else if (!schoolLeaverDto.getSex().matches("^[a-zA-Z\\s]+$")) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "sex", 
                "Sex can only contain letters and spaces"));
            isValid = false;
        }
        
        // Validate unit
        if (!ValidationUtil.isNotEmpty(schoolLeaverDto.getUnit())) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "unit", 
                "Unit is required"));
            isValid = false;
        } else if (!ValidationUtil.isValidLength(schoolLeaverDto.getUnit(), 1, Constants.MAX_UNIT_LENGTH)) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "unit", 
                "Unit must be between 1 and " + Constants.MAX_UNIT_LENGTH + " characters"));
            isValid = false;
        } else if (!schoolLeaverDto.getUnit().matches("^[a-zA-Z0-9\\s\\%]+$")) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "unit", 
                "Unit can only contain letters, numbers, spaces, and %"));
            isValid = false;
        }
        
        // Validate value
        if (schoolLeaverDto.getValue() == null) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "value", 
                "Value is required"));
            isValid = false;
        } else if (!ValidationUtil.isValidValue(schoolLeaverDto.getValue())) {
            bindingResult.addError(new FieldError("schoolLeaverDto", "value", 
                "Value must be between 0.0 and 999.99"));
            isValid = false;
        }
        
        if (isValid) {
            logger.info("School leaver form validation passed");
        } else {
            logger.warn("School leaver form validation failed with {} errors", bindingResult.getErrorCount());
        }
        
        return isValid;
    }
    
    /**
     * Validate user login form data
     * 
     * @param userDto the user data to validate
     * @param bindingResult the binding result to add errors to
     * @return true if valid, false otherwise
     */
    public boolean validateUserLoginForm(UserDto userDto, BindingResult bindingResult) {
        logger.info("Validating user login form data");
        
        boolean isValid = true;
        
        // Validate username
        if (!ValidationUtil.isNotEmpty(userDto.getUsername())) {
            bindingResult.addError(new FieldError("userDto", "username", 
                "Username is required"));
            isValid = false;
        } else if (!ValidationUtil.isValidLength(userDto.getUsername(), 4, 20)) {
            bindingResult.addError(new FieldError("userDto", "username", 
                "Username must be between 4 and 20 characters"));
            isValid = false;
        } else if (!userDto.getUsername().matches("^[a-zA-Z0-9]+$")) {
            bindingResult.addError(new FieldError("userDto", "username", 
                "Username can only contain letters and numbers"));
            isValid = false;
        }
        
        // Validate password
        if (!ValidationUtil.isNotEmpty(userDto.getPassword())) {
            bindingResult.addError(new FieldError("userDto", "password", 
                "Password is required"));
            isValid = false;
        } else if (!ValidationUtil.isValidLength(userDto.getPassword(), 4, 50)) {
            bindingResult.addError(new FieldError("userDto", "password", 
                "Password must be between 4 and 50 characters"));
            isValid = false;
        }
        
        if (isValid) {
            logger.info("User login form validation passed");
        } else {
            logger.warn("User login form validation failed with {} errors", bindingResult.getErrorCount());
        }
        
        return isValid;
    }
    
    /**
     * Validate user registration form data
     * 
     * @param userDto the user data to validate
     * @param bindingResult the binding result to add errors to
     * @return true if valid, false otherwise
     */
    public boolean validateUserRegistrationForm(UserDto userDto, BindingResult bindingResult) {
        logger.info("Validating user registration form data");
        
        boolean isValid = validateUserLoginForm(userDto, bindingResult);
        
        // Validate password confirmation
        if (!ValidationUtil.isNotEmpty(userDto.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userDto", "confirmPassword", 
                "Password confirmation is required"));
            isValid = false;
        } else if (!userDto.isPasswordConfirmed()) {
            bindingResult.addError(new FieldError("userDto", "confirmPassword", 
                "Passwords do not match"));
            isValid = false;
        }
        
        if (isValid) {
            logger.info("User registration form validation passed");
        } else {
            logger.warn("User registration form validation failed with {} errors", bindingResult.getErrorCount());
        }
        
        return isValid;
    }
    
    /**
     * Validate search form data
     * 
     * @param searchDto the search data to validate
     * @param bindingResult the binding result to add errors to
     * @return true if valid, false otherwise
     */
    public boolean validateSearchForm(SearchDto searchDto, BindingResult bindingResult) {
        logger.info("Validating search form data");
        
        boolean isValid = true;
        
        // Validate statistic code (optional)
        if (ValidationUtil.isNotEmpty(searchDto.getStatisticCode())) {
            if (!ValidationUtil.isValidLength(searchDto.getStatisticCode(), 0, 20)) {
                bindingResult.addError(new FieldError("searchDto", "statisticCode", 
                    "Statistic code must not exceed 20 characters"));
                isValid = false;
            } else if (!searchDto.getStatisticCode().matches("^[a-zA-Z0-9]*$")) {
                bindingResult.addError(new FieldError("searchDto", "statisticCode", 
                    "Statistic code can only contain letters and numbers"));
                isValid = false;
            }
        }
        
        // Validate quarter (optional)
        if (ValidationUtil.isNotEmpty(searchDto.getQuarter())) {
            if (!ValidationUtil.isValidLength(searchDto.getQuarter(), 0, 6)) {
                bindingResult.addError(new FieldError("searchDto", "quarter", 
                    "Quarter must not exceed 6 characters"));
                isValid = false;
            } else if (!searchDto.getQuarter().matches("^Q[1-4]\\d{4}$")) {
                bindingResult.addError(new FieldError("searchDto", "quarter", 
                    "Quarter must be in format Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY"));
                isValid = false;
            }
        }
        
        // Validate sex (optional)
        if (ValidationUtil.isNotEmpty(searchDto.getSex())) {
            if (!ValidationUtil.isValidLength(searchDto.getSex(), 0, 20)) {
                bindingResult.addError(new FieldError("searchDto", "sex", 
                    "Sex must not exceed 20 characters"));
                isValid = false;
            } else if (!searchDto.getSex().matches("^[a-zA-Z\\s]*$")) {
                bindingResult.addError(new FieldError("searchDto", "sex", 
                    "Sex can only contain letters and spaces"));
                isValid = false;
            }
        }
        
        // Validate pagination parameters
        if (searchDto.getPage() < 0) {
            bindingResult.addError(new FieldError("searchDto", "page", 
                "Page number must be 0 or greater"));
            isValid = false;
        }
        
        if (searchDto.getSize() < 1 || searchDto.getSize() > 100) {
            bindingResult.addError(new FieldError("searchDto", "size", 
                "Page size must be between 1 and 100"));
            isValid = false;
        }
        
        // Validate sort direction
        if (!searchDto.getSortDir().matches("^(asc|desc)$")) {
            bindingResult.addError(new FieldError("searchDto", "sortDir", 
                "Sort direction must be 'asc' or 'desc'"));
            isValid = false;
        }
        
        // Validate sort field
        if (!searchDto.getSortBy().matches("^[a-zA-Z_]*$")) {
            bindingResult.addError(new FieldError("searchDto", "sortBy", 
                "Sort field can only contain letters and underscores"));
            isValid = false;
        }
        
        if (isValid) {
            logger.info("Search form validation passed");
        } else {
            logger.warn("Search form validation failed with {} errors", bindingResult.getErrorCount());
        }
        
        return isValid;
    }
    
    /**
     * Get validation error messages as a map
     * 
     * @param bindingResult the binding result containing errors
     * @return map of field names to error messages
     */
    public Map<String, String> getValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        
        return errors;
    }
    
    /**
     * Sanitize input data
     * 
     * @param input the input string to sanitize
     * @return sanitized string
     */
    public String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        
        // Remove potentially dangerous characters
        return input.trim()
                   .replaceAll("[<>\"']", "")
                   .replaceAll("javascript:", "")
                   .replaceAll("on\\w+", "");
    }
    
    /**
     * Validate and sanitize school leaver data
     * 
     * @param schoolLeaverDto the school leaver data
     * @return sanitized school leaver data
     */
    public SchoolLeaverDto sanitizeSchoolLeaverData(SchoolLeaverDto schoolLeaverDto) {
        if (schoolLeaverDto == null) {
            return null;
        }
        
        SchoolLeaverDto sanitized = new SchoolLeaverDto();
        sanitized.setId(schoolLeaverDto.getId());
        sanitized.setStatisticCode(sanitizeInput(schoolLeaverDto.getStatisticCode()));
        sanitized.setStatisticLabel(sanitizeInput(schoolLeaverDto.getStatisticLabel()));
        sanitized.setQuarter(sanitizeInput(schoolLeaverDto.getQuarter()));
        sanitized.setSex(sanitizeInput(schoolLeaverDto.getSex()));
        sanitized.setUnit(sanitizeInput(schoolLeaverDto.getUnit()));
        sanitized.setValue(schoolLeaverDto.getValue()); // BigDecimal doesn't need sanitization
        
        return sanitized;
    }
} 