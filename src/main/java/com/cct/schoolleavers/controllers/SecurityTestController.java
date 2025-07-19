package com.cct.schoolleavers.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Security Test Controller
 * 
 * Simple controller to test Spring Security configuration.
 * Provides endpoints to verify authentication and authorization.
 * 
 * @author CCT Student
 * @version 1.0
 */
@RestController
@RequestMapping("/api/security")
public class SecurityTestController {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityTestController.class);
    
    /**
     * Test authentication status
     * 
     * @return authentication information
     */
    @GetMapping("/auth-status")
    public ResponseEntity<Map<String, Object>> getAuthStatus() {
        
        logger.info("Checking authentication status");
        
        Map<String, Object> response = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getName())) {
            
            response.put("authenticated", true);
            response.put("username", authentication.getName());
            response.put("authorities", authentication.getAuthorities());
            response.put("message", "User is authenticated");
            
            logger.info("User {} is authenticated", authentication.getName());
        } else {
            response.put("authenticated", false);
            response.put("message", "User is not authenticated");
            
            logger.info("User is not authenticated");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test protected endpoint
     * 
     * @return protected resource
     */
    @GetMapping("/protected")
    public ResponseEntity<Map<String, Object>> getProtectedResource() {
        
        logger.info("Accessing protected resource");
        
        Map<String, Object> response = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        response.put("success", true);
        response.put("message", "This is a protected resource");
        response.put("username", authentication.getName());
        response.put("timestamp", System.currentTimeMillis());
        
        logger.info("Protected resource accessed by user: {}", authentication.getName());
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test public endpoint
     * 
     * @return public resource
     */
    @GetMapping("/public")
    public ResponseEntity<Map<String, Object>> getPublicResource() {
        
        logger.info("Accessing public resource");
        
        Map<String, Object> response = new HashMap<>();
        
        response.put("success", true);
        response.put("message", "This is a public resource");
        response.put("timestamp", System.currentTimeMillis());
        
        logger.info("Public resource accessed");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test user information
     * 
     * @return current user information
     */
    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        
        logger.info("Getting user information");
        
        Map<String, Object> response = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getName())) {
            
            response.put("success", true);
            response.put("username", authentication.getName());
            response.put("authorities", authentication.getAuthorities());
            response.put("principal", authentication.getPrincipal().getClass().getSimpleName());
            response.put("authenticated", authentication.isAuthenticated());
            response.put("credentials", authentication.getCredentials() != null ? "present" : "null");
            
            logger.info("User information retrieved for: {}", authentication.getName());
        } else {
            response.put("success", false);
            response.put("message", "No authenticated user found");
            
            logger.info("No authenticated user found");
        }
        
        return ResponseEntity.ok(response);
    }
} 