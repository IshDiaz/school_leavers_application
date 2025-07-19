package com.cct.schoolleavers.config;

import com.cct.schoolleavers.entities.User;
import com.cct.schoolleavers.repositories.UserRepository;
import com.cct.schoolleavers.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Data Initializer
 * 
 * This class initializes the database with required data
 * when the application starts.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting data initialization...");
        
        // Initialize default user
        initializeDefaultUser();
        
        logger.info("Data initialization completed successfully.");
    }
    
    /**
     * Initialize the default user with required credentials
     */
    private void initializeDefaultUser() {
        try {
            // Check if default user already exists
            if (!userRepository.existsByUsername(Constants.DEFAULT_USERNAME)) {
                logger.info("Creating default user: {}", Constants.DEFAULT_USERNAME);
                
                // Create default user
                User defaultUser = new User();
                defaultUser.setUsername(Constants.DEFAULT_USERNAME);
                
                // Encode password (in a real application, you would use BCrypt)
                // For this simple system, we'll store the password as-is
                defaultUser.setPassword(Constants.DEFAULT_PASSWORD);
                defaultUser.setEnabled(true);
                
                // Save the user
                User savedUser = userRepository.save(defaultUser);
                
                logger.info("Default user created successfully with ID: {}", savedUser.getId());
            } else {
                logger.info("Default user already exists: {}", Constants.DEFAULT_USERNAME);
            }
            
        } catch (Exception e) {
            logger.error("Error creating default user: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Create additional test users if needed
     */
    private void createTestUsers() {
        try {
            // Create test user 1
            if (!userRepository.existsByUsername("testuser1")) {
                User testUser1 = new User();
                testUser1.setUsername("testuser1");
                testUser1.setPassword("password123");
                testUser1.setEnabled(true);
                userRepository.save(testUser1);
                logger.info("Test user 1 created: testuser1");
            }
            
            // Create test user 2
            if (!userRepository.existsByUsername("testuser2")) {
                User testUser2 = new User();
                testUser2.setUsername("testuser2");
                testUser2.setPassword("password456");
                testUser2.setEnabled(true);
                userRepository.save(testUser2);
                logger.info("Test user 2 created: testuser2");
            }
            
        } catch (Exception e) {
            logger.error("Error creating test users: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Display user statistics
     */
    private void displayUserStatistics() {
        try {
            long totalUsers = userRepository.count();
            long enabledUsers = userRepository.countByEnabled(true);
            long disabledUsers = userRepository.countByEnabled(false);
            
            logger.info("User Statistics:");
            logger.info("- Total Users: {}", totalUsers);
            logger.info("- Enabled Users: {}", enabledUsers);
            logger.info("- Disabled Users: {}", disabledUsers);
            
        } catch (Exception e) {
            logger.error("Error displaying user statistics: {}", e.getMessage(), e);
        }
    }
} 