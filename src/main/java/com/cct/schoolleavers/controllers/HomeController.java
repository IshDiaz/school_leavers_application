package com.cct.schoolleavers.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home Controller
 * 
 * Simple controller to handle home page and basic navigation.
 * Provides landing page and redirects for the school leavers system.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Controller
public class HomeController {
    
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    /**
     * Show home page
     * 
     * @param model the model
     * @return home page
     */
    @GetMapping("/")
    public String showHomePage(Model model) {
        logger.info("Showing home page");
        
        model.addAttribute("title", "School Leavers System");
        model.addAttribute("description", "A comprehensive CRUD application for managing school leavers data");
        
        return "index";
    }
    
    /**
     * Show dashboard (placeholder for now)
     * 
     * @param model the model
     * @return dashboard page
     */
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        logger.info("Showing dashboard");
        
        model.addAttribute("title", "Dashboard - School Leavers System");
        model.addAttribute("message", "Welcome to the School Leavers System Dashboard");
        
        // For now, redirect to a simple dashboard page
        // This will be expanded in future steps
        return "dashboard";
    }
} 