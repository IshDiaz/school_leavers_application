package com.cct.schoolleavers.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for User data
 * 
 * This class is used for form handling and data transfer between
 * the presentation layer and service layer for user authentication.
 * 
 * @author CCT Student
 * @version 1.0
 */
public class UserDto {
    
    private Long id;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain letters and numbers")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 100, message = "Password must be between 5 and 100 characters")
    private String password;
    
    private boolean enabled = true;
    
    // Default constructor
    public UserDto() {}
    
    // Constructor with all fields
    public UserDto(Long id, String username, String password, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
    
    // Constructor for login
    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                '}';
    }
} 