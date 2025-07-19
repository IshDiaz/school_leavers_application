package com.cct.schoolleavers.exception;

/**
 * Resource Not Found Exception
 * 
 * This exception is thrown when a requested resource is not found
 * in the system.
 * 
 * @author CCT Student
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
} 