package com.cct.schoolleavers.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Validation Utility class
 * 
 * This class provides common validation methods that can be used
 * throughout the application.
 * 
 * @author CCT Student
 * @version 1.0
 */
public class ValidationUtil {
    
    // Regex patterns
    private static final Pattern QUARTER_PATTERN = Pattern.compile("^[Q][1-4]\\d{4}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern STATISTIC_CODE_PATTERN = Pattern.compile("^[A-Z0-9]+$");
    
    /**
     * Validate quarter format (Q1YYYY, Q2YYYY, Q3YYYY, Q4YYYY)
     */
    public static boolean isValidQuarter(String quarter) {
        if (quarter == null || quarter.trim().isEmpty()) {
            return false;
        }
        return QUARTER_PATTERN.matcher(quarter.trim()).matches();
    }
    
    /**
     * Validate username format (alphanumeric only)
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username.trim()).matches();
    }
    
    /**
     * Validate statistic code format (uppercase letters and numbers only)
     */
    public static boolean isValidStatisticCode(String statisticCode) {
        if (statisticCode == null || statisticCode.trim().isEmpty()) {
            return false;
        }
        return STATISTIC_CODE_PATTERN.matcher(statisticCode.trim()).matches();
    }
    
    /**
     * Validate value range (0.0 to 999.99)
     */
    public static boolean isValidValue(BigDecimal value) {
        if (value == null) {
            return false;
        }
        return value.compareTo(BigDecimal.ZERO) >= 0 && 
               value.compareTo(new BigDecimal("999.99")) <= 0;
    }
    
    /**
     * Validate string length
     */
    public static boolean isValidLength(String value, int minLength, int maxLength) {
        if (value == null) {
            return minLength == 0;
        }
        int length = value.trim().length();
        return length >= minLength && length <= maxLength;
    }
    
    /**
     * Sanitize string input
     */
    public static String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().replaceAll("[<>\"']", "");
    }
    
    /**
     * Validate email format (basic validation)
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }
    
    /**
     * Check if string is not null and not empty
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Check if string is null or empty
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
} 