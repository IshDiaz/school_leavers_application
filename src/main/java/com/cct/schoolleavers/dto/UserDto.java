package com.cct.schoolleavers.dto;

import jakarta.validation.constraints.*;

/**
 * User Data Transfer Object
 * 
 * This DTO is used for form validation and data transfer between layers.
 * Contains simple validation annotations for backend form validation.
 * 
 * @author CCT Student
 * @version 1.0
 */
public class UserDto {
    
    private Long id;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 3, max = 50, message = "Password must be between 3 and 50 characters")
    private String password;
    
    private String confirmPassword;
    
    private Boolean enabled = true;
    
    // Default constructor
    public UserDto() {}
    
    // Constructor with all fields
    public UserDto(Long id, String username, String password, Boolean enabled) {
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
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * Validate password confirmation
     * 
     * @return true if passwords match, false otherwise
     */
    public boolean isPasswordConfirmed() {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.equals(confirmPassword);
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