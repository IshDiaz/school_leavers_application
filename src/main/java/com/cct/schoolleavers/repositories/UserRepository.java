package com.cct.schoolleavers.repositories;

import com.cct.schoolleavers.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User Repository Interface
 * 
 * This interface provides data access methods for the User entity.
 * It extends JpaRepository to inherit basic CRUD operations.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by username
     * 
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find user by username and enabled status
     * 
     * @param username the username to search for
     * @param enabled the enabled status
     * @return Optional containing the user if found
     */
    Optional<User> findByUsernameAndEnabled(String username, boolean enabled);
    
    /**
     * Check if user exists by username
     * 
     * @param username the username to check
     * @return true if user exists, false otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Find all enabled users
     * 
     * @return List of enabled users
     */
    List<User> findByEnabledTrue();
    
    /**
     * Find all disabled users
     * 
     * @return List of disabled users
     */
    List<User> findByEnabledFalse();
    
    /**
     * Find users created after a specific date
     * 
     * @param date the date to filter by
     * @return List of users created after the date
     */
    List<User> findByCreatedAtAfter(LocalDateTime date);
    
    /**
     * Find users who logged in after a specific date
     * 
     * @param date the date to filter by
     * @return List of users who logged in after the date
     */
    List<User> findByLastLoginAfter(LocalDateTime date);
    
    /**
     * Find users by username containing a pattern (case-insensitive)
     * 
     * @param usernamePattern the username pattern to search for
     * @return List of users matching the pattern
     */
    List<User> findByUsernameContainingIgnoreCase(String usernamePattern);
    
    /**
     * Count users by enabled status
     * 
     * @param enabled the enabled status
     * @return count of users with the specified enabled status
     */
    long countByEnabled(boolean enabled);
    
    /**
     * Find users with custom query for authentication
     * 
     * @param username the username
     * @param password the password
     * @return Optional containing the user if credentials match
     */
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.enabled = true")
    Optional<User> findByUsernameAndPasswordAndEnabled(@Param("username") String username, 
                                                      @Param("password") String password);
    
    /**
     * Update last login timestamp for a user
     * 
     * @param userId the user ID
     * @param lastLogin the last login timestamp
     */
    @Query("UPDATE User u SET u.lastLogin = :lastLogin, u.updatedAt = :lastLogin WHERE u.id = :userId")
    void updateLastLogin(@Param("userId") Long userId, @Param("lastLogin") LocalDateTime lastLogin);
    
    /**
     * Find users with pagination and sorting
     * 
     * @param enabled the enabled status filter
     * @return List of users with the specified enabled status
     */
    @Query("SELECT u FROM User u WHERE u.enabled = :enabled ORDER BY u.createdAt DESC")
    List<User> findEnabledUsersOrderByCreatedAt(@Param("enabled") boolean enabled);
    
    /**
     * Find users by creation date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return List of users created within the date range
     */
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate ORDER BY u.createdAt DESC")
    List<User> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);
    
    /**
     * Find inactive users (no login for specified days)
     * 
     * @param days the number of days to check
     * @return List of inactive users
     */
    @Query("SELECT u FROM User u WHERE u.lastLogin < :cutoffDate OR u.lastLogin IS NULL")
    List<User> findInactiveUsers(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    /**
     * Delete users by enabled status
     * 
     * @param enabled the enabled status
     */
    void deleteByEnabled(boolean enabled);
    
    /**
     * Find users with custom search criteria
     * 
     * @param usernamePattern the username pattern
     * @param enabled the enabled status
     * @return List of users matching the criteria
     */
    @Query("SELECT u FROM User u WHERE " +
           "(:usernamePattern IS NULL OR u.username LIKE %:usernamePattern%) AND " +
           "(:enabled IS NULL OR u.enabled = :enabled) " +
           "ORDER BY u.createdAt DESC")
    List<User> findUsersWithCriteria(@Param("usernamePattern") String usernamePattern, 
                                    @Param("enabled") Boolean enabled);
} 