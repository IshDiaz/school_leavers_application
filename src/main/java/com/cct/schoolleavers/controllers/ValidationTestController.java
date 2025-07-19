package com.cct.schoolleavers.controllers;

import com.cct.schoolleavers.dto.SchoolLeaverDto;
import com.cct.schoolleavers.dto.SearchDto;
import com.cct.schoolleavers.dto.UserDto;
import com.cct.schoolleavers.services.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Validation Test Controller
 * 
 * This controller provides endpoints to test form validation functionality.
 * It demonstrates simple validation scenarios for a simple system.
 * 
 * @author CCT Student
 * @version 1.0
 */
@RestController
@RequestMapping("/api/validation")
public class ValidationTestController {
    
    private static final Logger logger = LoggerFactory.getLogger(ValidationTestController.class);
    
    @Autowired
    private ValidationService validationService;
    
    /**
     * Test school leaver form validation
     * 
     * @param schoolLeaverDto the school leaver data to validate
     * @param bindingResult the binding result
     * @return validation result
     */
    @PostMapping("/school-leaver")
    public ResponseEntity<Map<String, Object>> testSchoolLeaverValidation(
            @Validated @RequestBody SchoolLeaverDto schoolLeaverDto,
            BindingResult bindingResult) {
        
        logger.info("Testing school leaver validation for: {}", schoolLeaverDto.getStatisticCode());
        
        Map<String, Object> response = new HashMap<>();
        
        if (bindingResult.hasErrors()) {
            response.put("valid", false);
            response.put("errors", validationService.getValidationErrors(bindingResult));
            response.put("message", "Validation failed");
            logger.warn("School leaver validation failed with {} errors", bindingResult.getErrorCount());
        } else {
            response.put("valid", true);
            response.put("message", "Validation passed");
            response.put("data", schoolLeaverDto);
            logger.info("School leaver validation passed");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test user login form validation
     * 
     * @param userDto the user data to validate
     * @param bindingResult the binding result
     * @return validation result
     */
    @PostMapping("/user-login")
    public ResponseEntity<Map<String, Object>> testUserLoginValidation(
            @Validated @RequestBody UserDto userDto,
            BindingResult bindingResult) {
        
        logger.info("Testing user login validation for: {}", userDto.getUsername());
        
        Map<String, Object> response = new HashMap<>();
        
        if (bindingResult.hasErrors()) {
            response.put("valid", false);
            response.put("errors", validationService.getValidationErrors(bindingResult));
            response.put("message", "Validation failed");
            logger.warn("User login validation failed with {} errors", bindingResult.getErrorCount());
        } else {
            response.put("valid", true);
            response.put("message", "Validation passed");
            response.put("data", userDto);
            logger.info("User login validation passed");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test user registration form validation
     * 
     * @param userDto the user data to validate
     * @param bindingResult the binding result
     * @return validation result
     */
    @PostMapping("/user-registration")
    public ResponseEntity<Map<String, Object>> testUserRegistrationValidation(
            @Validated @RequestBody UserDto userDto,
            BindingResult bindingResult) {
        
        logger.info("Testing user registration validation for: {}", userDto.getUsername());
        
        Map<String, Object> response = new HashMap<>();
        
        if (bindingResult.hasErrors()) {
            response.put("valid", false);
            response.put("errors", validationService.getValidationErrors(bindingResult));
            response.put("message", "Validation failed");
            logger.warn("User registration validation failed with {} errors", bindingResult.getErrorCount());
        } else {
            // Additional validation for password confirmation
            if (!userDto.isPasswordConfirmed()) {
                response.put("valid", false);
                Map<String, String> errors = new HashMap<>();
                errors.put("confirmPassword", "Passwords do not match");
                response.put("errors", errors);
                response.put("message", "Validation failed");
                logger.warn("User registration validation failed: passwords do not match");
            } else {
                response.put("valid", true);
                response.put("message", "Validation passed");
                response.put("data", userDto);
                logger.info("User registration validation passed");
            }
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test search form validation
     * 
     * @param searchDto the search data to validate
     * @param bindingResult the binding result
     * @return validation result
     */
    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> testSearchValidation(
            @Validated @RequestBody SearchDto searchDto,
            BindingResult bindingResult) {
        
        logger.info("Testing search validation");
        
        Map<String, Object> response = new HashMap<>();
        
        if (bindingResult.hasErrors()) {
            response.put("valid", false);
            response.put("errors", validationService.getValidationErrors(bindingResult));
            response.put("message", "Validation failed");
            logger.warn("Search validation failed with {} errors", bindingResult.getErrorCount());
        } else {
            response.put("valid", true);
            response.put("message", "Validation passed");
            response.put("data", searchDto);
            response.put("hasSearchCriteria", searchDto.hasSearchCriteria());
            logger.info("Search validation passed");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get validation examples
     * 
     * @return examples of valid and invalid data
     */
    @GetMapping("/examples")
    public ResponseEntity<Map<String, Object>> getValidationExamples() {
        
        Map<String, Object> examples = new HashMap<>();
        
        // Valid school leaver example
        SchoolLeaverDto validSchoolLeaver = new SchoolLeaverDto();
        validSchoolLeaver.setStatisticCode("SL001");
        validSchoolLeaver.setStatisticLabel("Total School Leavers");
        validSchoolLeaver.setQuarter("Q12023");
        validSchoolLeaver.setSex("Male");
        validSchoolLeaver.setUnit("Count");
        validSchoolLeaver.setValue(new BigDecimal("1250.50"));
        
        // Invalid school leaver example
        SchoolLeaverDto invalidSchoolLeaver = new SchoolLeaverDto();
        invalidSchoolLeaver.setStatisticCode(""); // Invalid: empty
        invalidSchoolLeaver.setStatisticLabel(""); // Invalid: empty
        invalidSchoolLeaver.setQuarter(""); // Invalid: empty
        invalidSchoolLeaver.setSex(""); // Invalid: empty
        invalidSchoolLeaver.setUnit(""); // Invalid: empty
        invalidSchoolLeaver.setValue(null); // Invalid: null
        
        // Valid user example
        UserDto validUser = new UserDto();
        validUser.setUsername("CCT1234");
        validUser.setPassword("54321");
        validUser.setConfirmPassword("54321");
        
        // Invalid user example
        UserDto invalidUser = new UserDto();
        invalidUser.setUsername(""); // Invalid: empty
        invalidUser.setPassword(""); // Invalid: empty
        invalidUser.setConfirmPassword("54321"); // Invalid: doesn't match
        
        // Valid search example
        SearchDto validSearch = new SearchDto();
        validSearch.setStatisticCode("SL001");
        validSearch.setQuarter("Q12023");
        validSearch.setSex("Male");
        validSearch.setPage(0);
        validSearch.setSize(10);
        validSearch.setSortDir("desc");
        validSearch.setSortBy("quarter");
        
        // Invalid search example
        SearchDto invalidSearch = new SearchDto();
        invalidSearch.setPage(-1); // Invalid: negative page
        invalidSearch.setSize(101); // Invalid: exceeds max
        
        examples.put("validSchoolLeaver", validSchoolLeaver);
        examples.put("invalidSchoolLeaver", invalidSchoolLeaver);
        examples.put("validUser", validUser);
        examples.put("invalidUser", invalidUser);
        examples.put("validSearch", validSearch);
        examples.put("invalidSearch", invalidSearch);
        
        return ResponseEntity.ok(examples);
    }
    
    /**
     * Test input sanitization
     * 
     * @param input the input to sanitize
     * @return sanitized input
     */
    @PostMapping("/sanitize")
    public ResponseEntity<Map<String, Object>> testSanitization(@RequestBody Map<String, String> request) {
        
        String input = request.get("input");
        String sanitized = validationService.sanitizeInput(input);
        
        Map<String, Object> response = new HashMap<>();
        response.put("original", input);
        response.put("sanitized", sanitized);
        response.put("message", "Input sanitization completed");
        
        logger.info("Input sanitization: '{}' -> '{}'", input, sanitized);
        
        return ResponseEntity.ok(response);
    }
} 