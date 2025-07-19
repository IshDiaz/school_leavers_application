package com.cct.schoolleavers.controllers;

import com.cct.schoolleavers.entities.User;
import com.cct.schoolleavers.entities.SchoolLeaver;
import com.cct.schoolleavers.repositories.UserRepository;
import com.cct.schoolleavers.repositories.SchoolLeaverRepository;
import com.cct.schoolleavers.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test Controller
 * 
 * This controller provides test endpoints to verify that the application
 * is working correctly, including user authentication and data access.
 * 
 * @author CCT Student
 * @version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {
    
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SchoolLeaverRepository schoolLeaverRepository;
    
    /**
     * Test endpoint to verify application is running
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "School Leavers System is running");
        response.put("timestamp", System.currentTimeMillis());
        response.put("version", Constants.APP_VERSION);
        
        logger.info("Health check requested");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test endpoint to verify user data
     */
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> testUsers() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Check if default user exists
            boolean defaultUserExists = userRepository.existsByUsername(Constants.DEFAULT_USERNAME);
            
            // Get user statistics
            long totalUsers = userRepository.count();
            long enabledUsers = userRepository.countByEnabled(true);
            long disabledUsers = userRepository.countByEnabled(false);
            
            // Get all users
            List<User> users = userRepository.findAll();
            
            response.put("status", "SUCCESS");
            response.put("defaultUserExists", defaultUserExists);
            response.put("defaultUsername", Constants.DEFAULT_USERNAME);
            response.put("defaultPassword", Constants.DEFAULT_PASSWORD);
            response.put("totalUsers", totalUsers);
            response.put("enabledUsers", enabledUsers);
            response.put("disabledUsers", disabledUsers);
            response.put("users", users);
            
            logger.info("User test completed - Total users: {}, Default user exists: {}", totalUsers, defaultUserExists);
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
            logger.error("Error testing users: {}", e.getMessage(), e);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test endpoint to verify school leavers data
     */
    @GetMapping("/school-leavers")
    public ResponseEntity<Map<String, Object>> testSchoolLeavers() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Get data statistics
            long totalRecords = schoolLeaverRepository.count();
            List<String> distinctCodes = schoolLeaverRepository.findDistinctStatisticCodes();
            List<String> distinctQuarters = schoolLeaverRepository.findDistinctQuarters();
            List<String> distinctSexes = schoolLeaverRepository.findDistinctSexes();
            
            // Get sample data
            List<SchoolLeaver> sampleData = schoolLeaverRepository.findAll().subList(0, Math.min(5, (int) totalRecords));
            
            response.put("status", "SUCCESS");
            response.put("totalRecords", totalRecords);
            response.put("distinctCodes", distinctCodes);
            response.put("distinctQuarters", distinctQuarters);
            response.put("distinctSexes", distinctSexes);
            response.put("sampleData", sampleData);
            
            logger.info("School leavers test completed - Total records: {}", totalRecords);
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
            logger.error("Error testing school leavers: {}", e.getMessage(), e);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test endpoint to verify database connection
     */
    @GetMapping("/database")
    public ResponseEntity<Map<String, Object>> testDatabase() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Test user repository
            long userCount = userRepository.count();
            
            // Test school leaver repository
            long schoolLeaverCount = schoolLeaverRepository.count();
            
            response.put("status", "SUCCESS");
            response.put("databaseConnection", "OK");
            response.put("userTableRecords", userCount);
            response.put("schoolLeaverTableRecords", schoolLeaverCount);
            response.put("message", "Database connection successful");
            
            logger.info("Database test completed - Users: {}, School Leavers: {}", userCount, schoolLeaverCount);
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("databaseConnection", "FAILED");
            response.put("message", e.getMessage());
            logger.error("Error testing database: {}", e.getMessage(), e);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test endpoint to verify authentication credentials
     */
    @GetMapping("/auth-test")
    public ResponseEntity<Map<String, Object>> testAuthentication() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Test default user authentication
            var defaultUser = userRepository.findByUsernameAndPasswordAndEnabled(
                Constants.DEFAULT_USERNAME, 
                Constants.DEFAULT_PASSWORD
            );
            
            boolean authSuccess = defaultUser.isPresent();
            
            response.put("status", "SUCCESS");
            response.put("authenticationTest", authSuccess ? "PASSED" : "FAILED");
            response.put("username", Constants.DEFAULT_USERNAME);
            response.put("password", Constants.DEFAULT_PASSWORD);
            response.put("userFound", authSuccess);
            
            if (authSuccess) {
                User user = defaultUser.get();
                response.put("userId", user.getId());
                response.put("userEnabled", user.isEnabled());
                response.put("userCreatedAt", user.getCreatedAt());
            }
            
            logger.info("Authentication test completed - Success: {}", authSuccess);
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("authenticationTest", "FAILED");
            response.put("message", e.getMessage());
            logger.error("Error testing authentication: {}", e.getMessage(), e);
        }
        
        return ResponseEntity.ok(response);
    }
} 