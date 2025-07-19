package com.cct.schoolleavers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * Password Encoder Configuration
 * 
 * This class provides password encoder beans for the application.
 * For this simple system, we use NoOpPasswordEncoder to store passwords as-is.
 * In a production environment, you should use BCryptPasswordEncoder.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Configuration
public class PasswordEncoderConfig {
    
    /**
     * Password encoder bean
     * 
     * Note: Using NoOpPasswordEncoder for simplicity in this assignment.
     * In a real application, you should use BCryptPasswordEncoder for security.
     * 
     * @return PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // For this simple system, we'll use NoOpPasswordEncoder
        // This allows us to store passwords as plain text for the assignment
        // In production, you should use BCryptPasswordEncoder
        return NoOpPasswordEncoder.getInstance();
    }
    
    /**
     * Alternative BCrypt password encoder (for production use)
     * 
     * Uncomment this method and comment out the above method for production use
     * 
     * @return BCryptPasswordEncoder instance
     */
    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    */
} 