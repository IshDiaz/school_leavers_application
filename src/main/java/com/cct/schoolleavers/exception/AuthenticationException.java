package com.cct.schoolleavers.exception;

/**
 * Authentication Exception
 * 
 * Thrown when authentication fails
 * 
 * @author CCT Student
 * @version 1.0
 */
public class AuthenticationException extends RuntimeException {
    
    public AuthenticationException(String message) {
        super(message);
    }
    
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
} 