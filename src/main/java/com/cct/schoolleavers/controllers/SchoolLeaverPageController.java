package com.cct.schoolleavers.controllers;

import com.cct.schoolleavers.dto.SchoolLeaverDto;
import com.cct.schoolleavers.entities.SchoolLeaver;
import com.cct.schoolleavers.services.SchoolLeaverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * School Leaver Page Controller
 * 
 * Handles web page routes for school leavers CRUD operations.
 * Provides views for list, add, edit, and view operations.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Controller
@RequestMapping("/school-leavers")
public class SchoolLeaverPageController {
    
    private static final Logger logger = LoggerFactory.getLogger(SchoolLeaverPageController.class);
    
    @Autowired
    private SchoolLeaverService schoolLeaverService;
    
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
     * @param redirectAttributes redirect attributes
     * @return edit form page
     */
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        logger.info("Showing edit school leaver page for ID: {}", id);
        
        try {
            SchoolLeaver schoolLeaver = schoolLeaverService.getSchoolLeaverById(id);
            
            SchoolLeaverDto dto = new SchoolLeaverDto();
            dto.setId(schoolLeaver.getId());
            dto.setStatisticCode(schoolLeaver.getStatisticCode());
            dto.setStatisticLabel(schoolLeaver.getStatisticLabel());
            dto.setQuarter(schoolLeaver.getQuarter());
            dto.setSex(schoolLeaver.getSex());
            dto.setUnit(schoolLeaver.getUnit());
            dto.setValue(schoolLeaver.getValue());
            
            model.addAttribute("title", "Edit School Leaver");
            model.addAttribute("description", "Update school leaver record");
            model.addAttribute("schoolLeaverDto", dto);
            
            return "school-leavers/edit";
        } catch (Exception e) {
            logger.error("Error loading edit page for ID {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Error loading record: " + e.getMessage());
            return "redirect:/school-leavers";
        }
    }
    
    /**
     * Show view page
     * 
     * @param id the record ID
     * @param model the model
     * @param redirectAttributes redirect attributes
     * @return view page
     */
    @GetMapping("/view/{id}")
    public String showViewPage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        logger.info("Showing view school leaver page for ID: {}", id);
        
        try {
            SchoolLeaver schoolLeaver = schoolLeaverService.getSchoolLeaverById(id);
            
            model.addAttribute("title", "View School Leaver");
            model.addAttribute("description", "Detailed information about the school leaver record");
            model.addAttribute("record", schoolLeaver);
            
            return "school-leavers/view";
        } catch (Exception e) {
            logger.error("Error loading view page for ID {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Error loading record: " + e.getMessage());
            return "redirect:/school-leavers";
        }
    }
    
    /**
     * Handle add form submission
     * 
     * @param schoolLeaverDto the school leaver DTO
     * @param redirectAttributes redirect attributes
     * @return redirect to list page
     */
    @PostMapping("/add")
    public String handleAdd(@ModelAttribute SchoolLeaverDto schoolLeaverDto, RedirectAttributes redirectAttributes) {
        logger.info("Handling add school leaver submission");
        
        try {
            SchoolLeaver created = schoolLeaverService.createSchoolLeaver(schoolLeaverDto);
            redirectAttributes.addFlashAttribute("success", "Record created successfully!");
            logger.info("School leaver record created with ID: {}", created.getId());
        } catch (Exception e) {
            logger.error("Error creating school leaver record: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Error creating record: " + e.getMessage());
        }
        
        return "redirect:/school-leavers";
    }
    
    /**
     * Handle edit form submission
     * 
     * @param id the record ID
     * @param schoolLeaverDto the school leaver DTO
     * @param redirectAttributes redirect attributes
     * @return redirect to list page
     */
    @PostMapping("/edit/{id}")
    public String handleEdit(@PathVariable Long id, @ModelAttribute SchoolLeaverDto schoolLeaverDto, RedirectAttributes redirectAttributes) {
        logger.info("Handling edit school leaver submission for ID: {}", id);
        
        try {
            SchoolLeaver updated = schoolLeaverService.updateSchoolLeaver(id, schoolLeaverDto);
            redirectAttributes.addFlashAttribute("success", "Record updated successfully!");
            logger.info("School leaver record updated with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error updating school leaver record with ID {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Error updating record: " + e.getMessage());
        }
        
        return "redirect:/school-leavers";
    }
    
    /**
     * Handle delete request
     * 
     * @param id the record ID
     * @param redirectAttributes redirect attributes
     * @return redirect to list page
     */
    @PostMapping("/delete/{id}")
    public String handleDelete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.info("Handling delete school leaver request for ID: {}", id);
        
        try {
            schoolLeaverService.deleteSchoolLeaver(id);
            redirectAttributes.addFlashAttribute("success", "Record deleted successfully!");
            logger.info("School leaver record deleted with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting school leaver record with ID {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Error deleting record: " + e.getMessage());
        }
        
        return "redirect:/school-leavers";
    }
} 