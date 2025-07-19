package com.cct.schoolleavers.exception;

/**
 * Data Access Exception
 * 
 * Thrown when there are errors accessing or manipulating data
 * 
 * @author CCT Student
 * @version 1.0
 */
public class DataAccessException extends RuntimeException {
    
    public DataAccessException(String message) {
        super(message);
    }
    
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
} 