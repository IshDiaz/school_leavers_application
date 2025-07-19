package com.cct.schoolleavers.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application Configuration class
 * 
 * This class holds application-specific configuration properties
 * that are loaded from application.properties file.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationConfig {
    
    private String name;
    private String version;
    private String description;
    private Pagination pagination = new Pagination();
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Pagination getPagination() {
        return pagination;
    }
    
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
    
    /**
     * Inner class for pagination configuration
     */
    public static class Pagination {
        private int defaultPageSize = 10;
        private int maxPageSize = 100;
        
        public int getDefaultPageSize() {
            return defaultPageSize;
        }
        
        public void setDefaultPageSize(int defaultPageSize) {
            this.defaultPageSize = defaultPageSize;
        }
        
        public int getMaxPageSize() {
            return maxPageSize;
        }
        
        public void setMaxPageSize(int maxPageSize) {
            this.maxPageSize = maxPageSize;
        }
    }
} 