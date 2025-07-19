package com.cct.schoolleavers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Database Configuration class
 * 
 * This class holds database-specific configuration properties
 * and can be extended for custom database configurations if needed.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Configuration
public class DatabaseConfig {
    
    @Value("${spring.datasource.url}")
    private String databaseUrl;
    
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;
    
    @Value("${spring.jpa.show-sql}")
    private boolean showSql;
    
    // Getters
    public String getDatabaseUrl() {
        return databaseUrl;
    }
    
    public String getDatabaseUsername() {
        return databaseUsername;
    }
    
    public String getDdlAuto() {
        return ddlAuto;
    }
    
    public boolean isShowSql() {
        return showSql;
    }
    
    /**
     * Get database name from URL
     * @return database name
     */
    public String getDatabaseName() {
        if (databaseUrl != null && databaseUrl.contains("/")) {
            String[] parts = databaseUrl.split("/");
            if (parts.length > 0) {
                String lastPart = parts[parts.length - 1];
                if (lastPart.contains("?")) {
                    return lastPart.substring(0, lastPart.indexOf("?"));
                }
                return lastPart;
            }
        }
        return "unknown";
    }
} 