package com.cct.schoolleavers.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Jackson Configuration
 * 
 * Configures Jackson for proper JSON serialization and deserialization.
 * Handles circular references and prevents infinite loops.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Configuration
public class JacksonConfig {
    
    /**
     * Configure ObjectMapper for JSON serialization
     * 
     * @return configured ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Configure serialization features
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        
        // Include only non-null values
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        // Register JavaTimeModule for LocalDateTime support
        objectMapper.registerModule(new JavaTimeModule());
        
        return objectMapper;
    }
} 