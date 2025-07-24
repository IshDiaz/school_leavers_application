package com.cct.schoolleavers.controllers;

import com.cct.schoolleavers.dto.SchoolLeaverDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * School Leaver Page Controller
 * 
 * Handles web page routes for school leavers CRUD operations.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Controller
@RequestMapping("/school-leavers")
public class SchoolLeaverPageController {
    
    private static final Logger logger = LoggerFactory.getLogger(SchoolLeaverPageController.class);
    
    /**
     * Show list page
     * 
     * @param model the model
     * @return list page
     */
    @GetMapping("")
    public String showListPage(Model model) {
        logger.info("Showing school leavers list page");
        
        model.addAttribute("title", "School Leavers Data");
        model.addAttribute("description", "Manage and view all school leavers records");
        
        return "school-leavers/list";
    }
    
    /**
     * Show add form page
     * 
     * @param model the model
     * @return add form page
     */
    @GetMapping("/add")
    public String showAddPage(Model model) {
        logger.info("Showing add school leaver page");
        
        model.addAttribute("title", "Add New School Leaver");
        model.addAttribute("description", "Create a new school leaver record");
        model.addAttribute("schoolLeaverDto", new SchoolLeaverDto());
        
        return "school-leavers/add";
    }
    
    /**
     * Show edit form page
     * 
     * @param id the record ID
     * @param model the model
     * @return edit form page
     */
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id, Model model) {
        logger.info("Showing edit school leaver page for ID: {}", id);
        
        model.addAttribute("title", "Edit School Leaver");
        model.addAttribute("description", "Update school leaver record");
        model.addAttribute("recordId", id);
        
        return "school-leavers/edit";
    }
    
    /**
     * Show view page
     * 
     * @param id the record ID
     * @param model the model
     * @return view page
     */
    @GetMapping("/view/{id}")
    public String showViewPage(@PathVariable Long id, Model model) {
        logger.info("Showing view school leaver page for ID: {}", id);
        
        model.addAttribute("title", "View School Leaver");
        model.addAttribute("description", "Detailed information about the school leaver record");
        model.addAttribute("recordId", id);
        
        return "school-leavers/view";
    }
} 