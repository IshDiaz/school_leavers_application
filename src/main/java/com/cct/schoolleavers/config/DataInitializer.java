package com.cct.schoolleavers.config;

import com.cct.schoolleavers.entities.User;
import com.cct.schoolleavers.entities.SchoolLeaver;
import com.cct.schoolleavers.repositories.UserRepository;
import com.cct.schoolleavers.repositories.SchoolLeaverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Data Initializer
 * 
 * Initializes default data when the application starts.
 * Ensures the default user exists with correct credentials.
 * Creates sample school leaver data for testing.
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
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("Initializing default data...");
        
        // Ensure default user exists
        createDefaultUser();
        
        // Create sample school leaver data (only if table exists)
        try {
            createSampleSchoolLeaverData();
        } catch (Exception e) {
            logger.warn("Could not create sample school leaver data: {}. Table may not exist yet.", e.getMessage());
        }
        
        logger.info("Data initialization completed.");
    }
    
    /**
     * Create default user if it doesn't exist
     */
    private void createDefaultUser() {
        try {
            String username = "CCT1234";
            String password = "54321";
            
            // Check if user already exists
            var existingUser = userRepository.findByUsername(username);
            
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                logger.info("Default user already exists: {}", username);
                
                // Update password if needed
                if (!password.equals(user.getPassword())) {
                    user.setPassword(password);
                    userRepository.save(user);
                    logger.info("Updated password for user: {}", username);
                }
            } else {
                // Create new user
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setEnabled(true);
                
                User savedUser = userRepository.save(user);
                logger.info("Created default user: {} with ID: {}", username, savedUser.getId());
            }
            
        } catch (Exception e) {
            logger.error("Error creating default user: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Create sample school leaver data for testing
     */
    private void createSampleSchoolLeaverData() {
        try {
            // Check if data already exists
            long existingCount = schoolLeaverRepository.count();
            if (existingCount > 0) {
                logger.info("School leaver data already exists ({} records), skipping sample data creation", existingCount);
                return;
            }
            
            logger.info("Creating sample school leaver data...");
            
            // Create sample records
            SchoolLeaver record1 = new SchoolLeaver();
            record1.setStatisticCode("SL001");
            record1.setStatisticLabel("School Leavers - Total");
            record1.setQuarter("2023-Q1");
            record1.setSex("All");
            record1.setUnit("Number");
            record1.setValue(new BigDecimal("1250.50"));
            schoolLeaverRepository.save(record1);
            
            SchoolLeaver record2 = new SchoolLeaver();
            record2.setStatisticCode("SL002");
            record2.setStatisticLabel("School Leavers - Male");
            record2.setQuarter("2023-Q1");
            record2.setSex("Male");
            record2.setUnit("Number");
            record2.setValue(new BigDecimal("650.25"));
            schoolLeaverRepository.save(record2);
            
            SchoolLeaver record3 = new SchoolLeaver();
            record3.setStatisticCode("SL003");
            record3.setStatisticLabel("School Leavers - Female");
            record3.setQuarter("2023-Q1");
            record3.setSex("Female");
            record3.setUnit("Number");
            record3.setValue(new BigDecimal("600.25"));
            schoolLeaverRepository.save(record3);
            
            SchoolLeaver record4 = new SchoolLeaver();
            record4.setStatisticCode("SL001");
            record4.setStatisticLabel("School Leavers - Total");
            record4.setQuarter("2023-Q2");
            record4.setSex("All");
            record4.setUnit("Number");
            record4.setValue(new BigDecimal("1300.75"));
            schoolLeaverRepository.save(record4);
            
            SchoolLeaver record5 = new SchoolLeaver();
            record5.setStatisticCode("SL002");
            record5.setStatisticLabel("School Leavers - Male");
            record5.setQuarter("2023-Q2");
            record5.setSex("Male");
            record5.setUnit("Number");
            record5.setValue(new BigDecimal("675.50"));
            schoolLeaverRepository.save(record5);
            
            logger.info("Sample school leaver data created successfully: 5 records");
            
        } catch (Exception e) {
            logger.error("Error creating sample school leaver data: {}", e.getMessage(), e);
            throw e; // Re-throw to be caught by the main method
        }
    }
} 