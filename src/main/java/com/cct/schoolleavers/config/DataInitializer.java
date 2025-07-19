package com.cct.schoolleavers.config;

import com.cct.schoolleavers.entities.User;
import com.cct.schoolleavers.entities.SchoolLeaver;
import com.cct.schoolleavers.repositories.UserRepository;
import com.cct.schoolleavers.repositories.SchoolLeaverRepository;
import com.cct.schoolleavers.util.Constants;
import com.cct.schoolleavers.util.DataImportUtil;
import java.util.List;
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
    private SchoolLeaverRepository schoolLeaverRepository;
    
    @Autowired
    private DataImportUtil dataImportUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting data initialization...");
        
        // Initialize default user
        initializeDefaultUser();
        
        // Note: School leavers data should be imported from your CSV dataset
        // initializeSampleSchoolLeaversData(); // Commented out since you have real data
        
        // Display statistics
        displayUserStatistics();
        displaySchoolLeaversStatistics();
        
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
    
    /**
     * Initialize sample school leavers data
     */
    private void initializeSampleSchoolLeaversData() {
        try {
            // Check if data already exists
            long existingCount = schoolLeaverRepository.count();
            if (existingCount > 0) {
                logger.info("School leavers data already exists ({} records), skipping sample data creation", existingCount);
                return;
            }
            
            logger.info("Creating sample school leavers data...");
            
            // Import sample data using the utility
            int importedCount = dataImportUtil.importSampleData();
            
            logger.info("Sample school leavers data created successfully: {} records", importedCount);
            
        } catch (Exception e) {
            logger.error("Error creating sample school leavers data: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Display school leavers statistics
     */
    private void displaySchoolLeaversStatistics() {
        try {
            long totalRecords = schoolLeaverRepository.count();
            
            if (totalRecords > 0) {
                logger.info("School Leavers Data Statistics:");
                logger.info("- Total Records: {}", totalRecords);
                
                // Get distinct values
                List<String> distinctCodes = schoolLeaverRepository.findDistinctStatisticCodes();
                List<String> distinctQuarters = schoolLeaverRepository.findDistinctQuarters();
                List<String> distinctSexes = schoolLeaverRepository.findDistinctSexes();
                
                logger.info("- Distinct Statistic Codes: {}", distinctCodes.size());
                logger.info("- Distinct Quarters: {}", distinctQuarters.size());
                logger.info("- Distinct Sexes: {}", distinctSexes.size());
                
                // Display some sample data
                List<SchoolLeaver> sampleRecords = schoolLeaverRepository.findAll().subList(0, Math.min(3, (int) totalRecords));
                logger.info("Sample Records:");
                for (SchoolLeaver record : sampleRecords) {
                    logger.info("  - {}: {} ({}) = {}", 
                        record.getStatisticCode(), 
                        record.getStatisticLabel(), 
                        record.getQuarter(), 
                        record.getValue());
                }
            } else {
                logger.info("No school leavers data found");
            }
            
        } catch (Exception e) {
            logger.error("Error displaying school leavers statistics: {}", e.getMessage(), e);
        }
    }
} 