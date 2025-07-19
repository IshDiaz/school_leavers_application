package com.cct.schoolleavers.services;

import com.cct.schoolleavers.dto.UserDto;
import com.cct.schoolleavers.entities.User;
import com.cct.schoolleavers.exception.ResourceNotFoundException;
import com.cct.schoolleavers.repositories.UserRepository;
import com.cct.schoolleavers.util.Constants;
import com.cct.schoolleavers.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User Service
 * 
 * This service provides simple authentication and user management functionality.
 * It uses basic authentication without complex security features for the assessment.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Service
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Authenticate user with username and password
     * 
     * @param username the username
     * @param password the password
     * @return User if authentication successful, null otherwise
     */
    public User authenticateUser(String username, String password) {
        try {
            logger.info("Attempting authentication for user: {}", username);
            
            // Validate input
            if (!ValidationUtil.isNotEmpty(username) || !ValidationUtil.isNotEmpty(password)) {
                logger.warn("Authentication failed: Empty username or password");
                return null;
            }
            
            // Find user by username
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isEmpty()) {
                logger.warn("Authentication failed: User not found - {}", username);
                return null;
            }
            
            User user = userOptional.get();
            
            // Check if user is enabled
            if (!user.isEnabled()) {
                logger.warn("Authentication failed: User disabled - {}", username);
                return null;
            }
            
            // Simple password check (for assessment purposes)
            if (passwordEncoder.matches(password, user.getPassword()) || 
                password.equals(user.getPassword())) {
                
                // Update last login
                user.updateLastLogin();
                userRepository.save(user);
                
                logger.info("Authentication successful for user: {}", username);
                return user;
            } else {
                logger.warn("Authentication failed: Invalid password for user - {}", username);
                return null;
            }
            
        } catch (Exception e) {
            logger.error("Error during authentication for user {}: {}", username, e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Create a new user
     * 
     * @param userDto the user data
     * @return created user
     */
    public User createUser(UserDto userDto) {
        try {
            logger.info("Creating new user: {}", userDto.getUsername());
            
            // Validate input
            if (!ValidationUtil.isNotEmpty(userDto.getUsername()) || 
                !ValidationUtil.isNotEmpty(userDto.getPassword())) {
                throw new IllegalArgumentException("Username and password are required");
            }
            
            // Check if username already exists
            if (userRepository.existsByUsername(userDto.getUsername())) {
                throw new IllegalArgumentException("Username already exists: " + userDto.getUsername());
            }
            
            // Create new user
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword()); // Simple password storage for assessment
            user.setEnabled(userDto.isEnabled());
            
            User savedUser = userRepository.save(user);
            logger.info("User created successfully: {}", savedUser.getUsername());
            
            return savedUser;
            
        } catch (Exception e) {
            logger.error("Error creating user {}: {}", userDto.getUsername(), e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get user by ID
     * 
     * @param id the user ID
     * @return user if found
     * @throws ResourceNotFoundException if user not found
     */
    public User getUserById(Long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new ResourceNotFoundException("User not found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error getting user by ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get user by username
     * 
     * @param username the username
     * @return user if found
     * @throws ResourceNotFoundException if user not found
     */
    public User getUserByUsername(String username) {
        try {
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new ResourceNotFoundException("User not found with username: " + username);
            }
        } catch (Exception e) {
            logger.error("Error getting user by username {}: {}", username, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get all users
     * 
     * @return list of all users
     */
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Error getting all users: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get enabled users only
     * 
     * @return list of enabled users
     */
    public List<User> getEnabledUsers() {
        try {
            return userRepository.findByEnabledTrue();
        } catch (Exception e) {
            logger.error("Error getting enabled users: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Update user
     * 
     * @param id the user ID
     * @param userDto the updated user data
     * @return updated user
     * @throws ResourceNotFoundException if user not found
     */
    public User updateUser(Long id, UserDto userDto) {
        try {
            logger.info("Updating user with ID: {}", id);
            
            User existingUser = getUserById(id);
            
            // Update fields
            if (ValidationUtil.isNotEmpty(userDto.getUsername())) {
                // Check if new username already exists (excluding current user)
                if (!existingUser.getUsername().equals(userDto.getUsername()) &&
                    userRepository.existsByUsername(userDto.getUsername())) {
                    throw new IllegalArgumentException("Username already exists: " + userDto.getUsername());
                }
                existingUser.setUsername(userDto.getUsername());
            }
            
            if (ValidationUtil.isNotEmpty(userDto.getPassword())) {
                existingUser.setPassword(userDto.getPassword());
            }
            
            existingUser.setEnabled(userDto.isEnabled());
            
            User updatedUser = userRepository.save(existingUser);
            logger.info("User updated successfully: {}", updatedUser.getUsername());
            
            return updatedUser;
            
        } catch (Exception e) {
            logger.error("Error updating user with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Delete user
     * 
     * @param id the user ID
     * @throws ResourceNotFoundException if user not found
     */
    public void deleteUser(Long id) {
        try {
            logger.info("Deleting user with ID: {}", id);
            
            User user = getUserById(id);
            userRepository.delete(user);
            
            logger.info("User deleted successfully: {}", user.getUsername());
            
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Enable or disable user
     * 
     * @param id the user ID
     * @param enabled the enabled status
     * @return updated user
     * @throws ResourceNotFoundException if user not found
     */
    public User setUserEnabled(Long id, boolean enabled) {
        try {
            logger.info("Setting user enabled status - ID: {}, Enabled: {}", id, enabled);
            
            User user = getUserById(id);
            user.setEnabled(enabled);
            
            User updatedUser = userRepository.save(user);
            logger.info("User enabled status updated: {} - {}", updatedUser.getUsername(), enabled);
            
            return updatedUser;
            
        } catch (Exception e) {
            logger.error("Error setting user enabled status for ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Check if user exists by username
     * 
     * @param username the username
     * @return true if user exists, false otherwise
     */
    public boolean userExists(String username) {
        try {
            return userRepository.existsByUsername(username);
        } catch (Exception e) {
            logger.error("Error checking if user exists {}: {}", username, e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Get user count
     * 
     * @return total number of users
     */
    public long getUserCount() {
        try {
            return userRepository.count();
        } catch (Exception e) {
            logger.error("Error getting user count: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    /**
     * Get enabled user count
     * 
     * @return number of enabled users
     */
    public long getEnabledUserCount() {
        try {
            return userRepository.countByEnabled(true);
        } catch (Exception e) {
            logger.error("Error getting enabled user count: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    /**
     * Validate user credentials (simple validation)
     * 
     * @param userDto the user data to validate
     * @return true if valid, false otherwise
     */
    public boolean validateUserCredentials(UserDto userDto) {
        try {
            // Check username
            if (!ValidationUtil.isNotEmpty(userDto.getUsername()) ||
                !ValidationUtil.isValidLength(userDto.getUsername(), 
                    Constants.MIN_USERNAME_LENGTH, Constants.MAX_USERNAME_LENGTH) ||
                !ValidationUtil.isValidUsername(userDto.getUsername())) {
                return false;
            }
            
            // Check password
            if (!ValidationUtil.isNotEmpty(userDto.getPassword()) ||
                !ValidationUtil.isValidLength(userDto.getPassword(), 
                    Constants.MIN_PASSWORD_LENGTH, Constants.MAX_PASSWORD_LENGTH)) {
                return false;
            }
            
            return true;
            
        } catch (Exception e) {
            logger.error("Error validating user credentials: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Get default user (for testing purposes)
     * 
     * @return default user if exists
     */
    public User getDefaultUser() {
        try {
            return getUserByUsername(Constants.DEFAULT_USERNAME);
        } catch (Exception e) {
            logger.error("Error getting default user: {}", e.getMessage(), e);
            return null;
        }
    }
} 