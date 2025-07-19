package com.cct.schoolleavers.controllers;

import com.cct.schoolleavers.dto.UserDto;
import com.cct.schoolleavers.entities.User;
import com.cct.schoolleavers.services.UserService;
import com.cct.schoolleavers.services.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * Login Controller
 * 
 * This controller handles authentication, login, and logout functionality.
 * Provides simple login/logout operations for the school leavers system.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Controller
public class LoginController {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ValidationService validationService;
    
    /**
     * Show login page
     * 
     * @param model the model
     * @return login page
     */
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        logger.info("Showing login page");
        
        if (!model.containsAttribute("userDto")) {
            model.addAttribute("userDto", new UserDto());
        }
        
        return "auth/login";
    }
    
    /**
     * Handle login form submission
     * 
     * @param userDto the user data
     * @param bindingResult the binding result
     * @param session the HTTP session
     * @param redirectAttributes the redirect attributes
     * @return redirect to dashboard or back to login
     */
    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("userDto") UserDto userDto,
                             BindingResult bindingResult,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        
        logger.info("Processing login for user: {}", userDto.getUsername());
        
        // Validate form data
        if (bindingResult.hasErrors()) {
            logger.warn("Login validation failed with {} errors", bindingResult.getErrorCount());
            redirectAttributes.addFlashAttribute("error", "Please correct the errors below");
            redirectAttributes.addFlashAttribute("userDto", userDto);
            return "redirect:/login";
        }
        
        try {
            // Sanitize input
            userDto.setUsername(userDto.getUsername().trim());
            userDto.setPassword(userDto.getPassword().trim());
            
            // Authenticate user
            User authenticatedUser = userService.authenticateUser(userDto.getUsername(), userDto.getPassword());
            
            if (authenticatedUser != null) {
                // Store user info in session
                session.setAttribute("username", userDto.getUsername());
                session.setAttribute("isLoggedIn", true);
                session.setAttribute("loginTime", System.currentTimeMillis());
                
                logger.info("User {} logged in successfully", userDto.getUsername());
                redirectAttributes.addFlashAttribute("success", "Welcome, " + userDto.getUsername() + "!");
                
                return "redirect:/dashboard";
            } else {
                logger.warn("Login failed for user: {}", userDto.getUsername());
                redirectAttributes.addFlashAttribute("error", "Invalid username or password");
                redirectAttributes.addFlashAttribute("userDto", userDto);
                return "redirect:/login";
            }
            
        } catch (Exception e) {
            logger.error("Error during login: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "An error occurred during login. Please try again.");
            redirectAttributes.addFlashAttribute("userDto", userDto);
            return "redirect:/login";
        }
    }
    
    /**
     * Handle logout
     * 
     * @param session the HTTP session
     * @param redirectAttributes the redirect attributes
     * @return redirect to login page
     */
    @GetMapping("/logout")
    public String handleLogout(HttpSession session, RedirectAttributes redirectAttributes) {
        
        String username = (String) session.getAttribute("username");
        logger.info("User {} logging out", username);
        
        // Invalidate session
        session.invalidate();
        
        redirectAttributes.addFlashAttribute("success", "You have been logged out successfully");
        return "redirect:/login";
    }
    
    /**
     * Show access denied page
     * 
     * @param model the model
     * @return access denied page
     */
    @GetMapping("/access-denied")
    public String showAccessDenied(Model model) {
        logger.warn("Access denied page accessed");
        
        model.addAttribute("error", "Access Denied");
        model.addAttribute("message", "You do not have permission to access this page.");
        model.addAttribute("status", "403");
        
        return "error/access-denied";
    }
    
    /**
     * Show unauthorized page
     * 
     * @param model the model
     * @return unauthorized page
     */
    @GetMapping("/unauthorized")
    public String showUnauthorized(Model model) {
        logger.warn("Unauthorized page accessed");
        
        model.addAttribute("error", "Unauthorized");
        model.addAttribute("message", "Please log in to access this page.");
        model.addAttribute("status", "401");
        
        return "error/unauthorized";
    }
    
    /**
     * Check if user is logged in
     * 
     * @param session the HTTP session
     * @return true if logged in, false otherwise
     */
    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("isLoggedIn") != null && 
               (Boolean) session.getAttribute("isLoggedIn");
    }
    
    /**
     * Get current username from session
     * 
     * @param session the HTTP session
     * @return username or null if not logged in
     */
    public static String getCurrentUsername(HttpSession session) {
        if (isLoggedIn(session)) {
            return (String) session.getAttribute("username");
        }
        return null;
    }
    
    /**
     * Require authentication - redirect to login if not authenticated
     * 
     * @param session the HTTP session
     * @param redirectAttributes the redirect attributes
     * @return true if authenticated, false if redirected
     */
    public static boolean requireAuth(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Please log in to access this page");
            return false;
        }
        return true;
    }
} 