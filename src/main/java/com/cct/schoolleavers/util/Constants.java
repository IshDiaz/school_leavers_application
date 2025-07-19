package com.cct.schoolleavers.util;

/**
 * Application Constants
 * 
 * This class contains all application-wide constants used
 * throughout the system.
 * 
 * @author CCT Student
 * @version 1.0
 */
public final class Constants {
    
    // Private constructor to prevent instantiation
    private Constants() {}
    
    // ===========================================
    // APPLICATION CONSTANTS
    // ===========================================
    public static final String APP_NAME = "School Leavers CRUD System";
    public static final String APP_VERSION = "1.0.0";
    public static final String APP_DESCRIPTION = "Spring Boot CRUD application for managing school leavers data";
    
    // ===========================================
    // SECURITY CONSTANTS
    // ===========================================
    public static final String DEFAULT_USERNAME = "CCT1234";
    public static final String DEFAULT_PASSWORD = "54321";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    
    // ===========================================
    // VALIDATION CONSTANTS
    // ===========================================
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MIN_PASSWORD_LENGTH = 5;
    public static final int MAX_PASSWORD_LENGTH = 100;
    public static final int MAX_STATISTIC_CODE_LENGTH = 20;
    public static final int MAX_STATISTIC_LABEL_LENGTH = 100;
    public static final int MAX_QUARTER_LENGTH = 10;
    public static final int MAX_SEX_LENGTH = 20;
    public static final int MAX_UNIT_LENGTH = 10;
    public static final double MIN_VALUE = 0.0;
    public static final double MAX_VALUE = 999.99;
    
    // ===========================================
    // PAGINATION CONSTANTS
    // ===========================================
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String DEFAULT_SORT_FIELD = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    
    // ===========================================
    // MESSAGE CONSTANTS
    // ===========================================
    public static final String SUCCESS_CREATE = "Record created successfully";
    public static final String SUCCESS_UPDATE = "Record updated successfully";
    public static final String SUCCESS_DELETE = "Record deleted successfully";
    public static final String ERROR_CREATE = "Failed to create record";
    public static final String ERROR_UPDATE = "Failed to update record";
    public static final String ERROR_DELETE = "Failed to delete record";
    public static final String ERROR_NOT_FOUND = "Record not found";
    public static final String ERROR_VALIDATION = "Validation failed";
    public static final String ERROR_AUTHENTICATION = "Authentication failed";
    public static final String ERROR_AUTHORIZATION = "Access denied";
    
    // ===========================================
    // URL CONSTANTS
    // ===========================================
    public static final String URL_HOME = "/";
    public static final String URL_LOGIN = "/login";
    public static final String URL_LOGOUT = "/logout";
    public static final String URL_DASHBOARD = "/dashboard";
    public static final String URL_SCHOOL_LEAVERS = "/school-leavers";
    public static final String URL_ERROR = "/error";
    
    // ===========================================
    // VIEW CONSTANTS
    // ===========================================
    public static final String VIEW_LOGIN = "auth/login";
    public static final String VIEW_DASHBOARD = "dashboard/index";
    public static final String VIEW_SCHOOL_LEAVERS_LIST = "school-leavers/list";
    public static final String VIEW_SCHOOL_LEAVERS_FORM = "school-leavers/form";
    public static final String VIEW_ERROR_404 = "error/404";
    public static final String VIEW_ERROR_500 = "error/500";
    public static final String VIEW_ERROR_VALIDATION = "error/validation";
    
    // ===========================================
    // DATABASE CONSTANTS
    // ===========================================
    public static final String TABLE_USERS = "users";
    public static final String TABLE_SCHOOL_LEAVERS = "school_leavers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ENABLED = "enabled";
    public static final String COLUMN_STATISTIC_CODE = "statistic_code";
    public static final String COLUMN_STATISTIC_LABEL = "statistic_label";
    public static final String COLUMN_QUARTER = "quarter";
    public static final String COLUMN_SEX = "sex";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_VALUE = "value";
    
    // ===========================================
    // REGEX PATTERNS
    // ===========================================
    public static final String PATTERN_QUARTER = "^[Q][1-4]\\d{4}$";
    public static final String PATTERN_USERNAME = "^[a-zA-Z0-9]+$";
    public static final String PATTERN_STATISTIC_CODE = "^[A-Z0-9]+$";
    public static final String PATTERN_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
} 