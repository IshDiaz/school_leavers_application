package com.cct.schoolleavers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Global Exception Handler
 * 
 * This class handles application-wide exceptions and provides
 * consistent error responses across the application.
 * 
 * @author CCT Student
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());
    
    /**
     * Handle validation exceptions
     */
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException ex, Model model) {
        logger.warning("Validation error: " + ex.getMessage());
        
        model.addAttribute("error", "Please correct the following errors:");
        model.addAttribute("bindingResult", ex.getBindingResult());
        
        return "error/validation";
    }
    
    /**
     * Handle resource not found exceptions
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        logger.warning("Resource not found: " + ex.getMessage());
        
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("status", "404");
        model.addAttribute("message", "The requested resource was not found");
        
        return "error/404";
    }
    
    /**
     * Handle general runtime exceptions
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        logger.severe("Runtime error: " + ex.getMessage());
        
        model.addAttribute("error", "An unexpected error occurred");
        model.addAttribute("status", "500");
        model.addAttribute("message", "Please try again later");
        
        return "error/500";
    }
    
    /**
     * Handle general exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        logger.severe("General error: " + ex.getMessage());
        
        model.addAttribute("error", "An error occurred");
        model.addAttribute("status", "500");
        model.addAttribute("message", "Please contact the administrator");
        
        return "error/500";
    }
    
    /**
     * Handle exceptions with redirect attributes
     */
    @ExceptionHandler(Exception.class)
    public String handleExceptionWithRedirect(Exception ex, RedirectAttributes redirectAttributes, 
                                            HttpServletRequest request) {
        logger.severe("Error in request " + request.getRequestURI() + ": " + ex.getMessage());
        
        redirectAttributes.addFlashAttribute("error", "An error occurred. Please try again.");
        
        return "redirect:/";
    }
} 