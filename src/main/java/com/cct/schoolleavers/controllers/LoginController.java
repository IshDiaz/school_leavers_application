package com.cct.schoolleavers.controllers;

import com.cct.schoolleavers.entities.User;
import com.cct.schoolleavers.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

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
    
    /**
     * Show login page
     * 
     * @param model the model
     * @return login page
     */
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        logger.info("Showing login page");
        
        return "auth/login";
    }
    
    /**
     * Handle login form submission
     * 
     * @param username the username
     * @param password the password
     * @param session the HTTP session
     * @param redirectAttributes redirect attributes
     * @return redirect to dashboard or login page
     */
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                             @RequestParam String password,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        
        logger.info("Login attempt for user: {}", username);
        
        try {
            // Simple authentication
            User user = userService.authenticateUser(username, password);
            
            if (user != null) {
                // Set session attributes
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("authenticated", true);
                
                logger.info("User {} logged in successfully", username);
                redirectAttributes.addFlashAttribute("success", "Welcome, " + username + "!");
                
                return "redirect:/dashboard";
            } else {
                logger.warn("Failed login attempt for user: {}", username);
                redirectAttributes.addFlashAttribute("error", "Invalid username or password. Please try again.");
                return "redirect:/login";
            }
            
        } catch (Exception e) {
            logger.error("Error during login for user {}: {}", username, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "An error occurred during login. Please try again.");
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
        return session.getAttribute("authenticated") != null && 
               (Boolean) session.getAttribute("authenticated");
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