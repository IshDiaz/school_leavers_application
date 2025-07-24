package com.cct.schoolleavers.controllers;

import com.cct.schoolleavers.dto.SchoolLeaverDto;
import com.cct.schoolleavers.entities.SchoolLeaver;
import com.cct.schoolleavers.services.SchoolLeaverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * School Leaver REST API Controller
 * 
 * Handles REST API endpoints for school leavers CRUD operations.
 * 
 * @author CCT Student
 * @version 1.0
 */
@RestController
@RequestMapping("/api/school-leavers")
public class SchoolLeaverApiController {
    
    private static final Logger logger = LoggerFactory.getLogger(SchoolLeaverApiController.class);
    
    @Autowired
    private SchoolLeaverService schoolLeaverService;
    
    /**
     * Get all school leavers
     * 
     * @return JSON response with all school leavers
     */
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllSchoolLeavers() {
        logger.info("Getting all school leavers");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<SchoolLeaver> schoolLeavers = schoolLeaverService.getAllSchoolLeavers();
            
            response.put("success", true);
            response.put("data", schoolLeavers);
            response.put("count", schoolLeavers.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error getting all school leavers: {}", e.getMessage(), e);
            
            response.put("success", false);
            response.put("error", "Failed to retrieve school leavers");
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Get school leaver by ID
     * 
     * @param id the record ID
     * @return JSON response with school leaver data
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSchoolLeaverById(@PathVariable Long id) {
        logger.info("Getting school leaver with ID: {}", id);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            SchoolLeaver schoolLeaver = schoolLeaverService.getSchoolLeaverById(id);
            
            response.put("success", true);
            response.put("data", schoolLeaver);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error getting school leaver with ID {}: {}", id, e.getMessage(), e);
            
            response.put("success", false);
            response.put("error", "Failed to retrieve school leaver");
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Create new school leaver
     * 
     * @param schoolLeaverDto the school leaver data
     * @return JSON response
     */
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createSchoolLeaver(@RequestBody SchoolLeaverDto schoolLeaverDto) {
        logger.info("Creating new school leaver");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            SchoolLeaver created = schoolLeaverService.createSchoolLeaver(schoolLeaverDto);
            
            response.put("success", true);
            response.put("message", "School leaver created successfully");
            response.put("data", created);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            logger.error("Error creating school leaver: {}", e.getMessage(), e);
            
            response.put("success", false);
            response.put("error", "Failed to create school leaver");
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Update school leaver
     * 
     * @param id the record ID
     * @param schoolLeaverDto the updated data
     * @return JSON response
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSchoolLeaver(@PathVariable Long id, @RequestBody SchoolLeaverDto schoolLeaverDto) {
        logger.info("Updating school leaver with ID: {}", id);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            SchoolLeaver updated = schoolLeaverService.updateSchoolLeaver(id, schoolLeaverDto);
            
            response.put("success", true);
            response.put("message", "School leaver updated successfully");
            response.put("data", updated);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error updating school leaver with ID {}: {}", id, e.getMessage(), e);
            
            response.put("success", false);
            response.put("error", "Failed to update school leaver");
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Delete school leaver
     * 
     * @param id the record ID
     * @return JSON response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSchoolLeaver(@PathVariable Long id) {
        logger.info("Deleting school leaver with ID: {}", id);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            schoolLeaverService.deleteSchoolLeaver(id);
            
            response.put("success", true);
            response.put("message", "School leaver deleted successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error deleting school leaver with ID {}: {}", id, e.getMessage(), e);
            
            response.put("success", false);
            response.put("error", "Failed to delete school leaver");
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 
 