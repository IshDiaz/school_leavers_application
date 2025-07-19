package com.cct.schoolleavers.exception;

/**
 * Unauthorized Exception
 * 
 * Thrown when access is denied due to insufficient permissions
 * 
 * @author CCT Student
 * @version 1.0
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
    
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
} 