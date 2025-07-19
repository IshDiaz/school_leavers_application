package com.cct.schoolleavers.controllers;

import com.cct.schoolleavers.dto.SchoolLeaverDto;
import com.cct.schoolleavers.dto.SearchDto;
import com.cct.schoolleavers.entities.SchoolLeaver;
import com.cct.schoolleavers.services.SchoolLeaverService;
import com.cct.schoolleavers.services.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * School Leaver Controller
 * 
 * This controller provides REST endpoints for CRUD operations on school leavers data.
 * Includes search, pagination, and statistical analysis endpoints.
 * 
 * @author CCT Student
 * @version 1.0
 */
@RestController
@RequestMapping("/api/school-leavers")
public class SchoolLeaverController {
    
    private static final Logger logger = LoggerFactory.getLogger(SchoolLeaverController.class);
    
    @Autowired
    private SchoolLeaverService schoolLeaverService;
    
    @Autowired
    private ValidationService validationService;
    
    /**
     * Create a new school leaver record
     * 
     * @param schoolLeaverDto the school leaver data
     * @param bindingResult the binding result
     * @param session the HTTP session
     * @return created school leaver
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createSchoolLeaver(
            @Valid @RequestBody SchoolLeaverDto schoolLeaverDto,
            BindingResult bindingResult,
            HttpSession session) {
        
        logger.info("Creating new school leaver record: {}", schoolLeaverDto.getStatisticCode());
        
        Map<String, Object> response = new HashMap<>();
        
        // Check authentication
        if (!LoginController.isLoggedIn(session)) {
            response.put("error", "Authentication required");
            response.put("message", "Please log in to perform this operation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        // Validate form data
        if (bindingResult.hasErrors()) {
            response.put("error", "Validation failed");
            response.put("message", "Please correct the errors below");
            response.put("errors", validationService.getValidationErrors(bindingResult));
            logger.warn("School leaver creation failed with {} validation errors", bindingResult.getErrorCount());
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            // Sanitize and create school leaver
            SchoolLeaverDto sanitizedDto = validationService.sanitizeSchoolLeaverData(schoolLeaverDto);
            SchoolLeaver createdSchoolLeaver = schoolLeaverService.createSchoolLeaver(sanitizedDto);
            
            response.put("success", true);
            response.put("message", "School leaver record created successfully");
            response.put("data", createdSchoolLeaver);
            response.put("id", createdSchoolLeaver.getId());
            
            logger.info("School leaver record created successfully with ID: {}", createdSchoolLeaver.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("error", "Creation failed");
            response.put("message", "An error occurred while creating the school leaver record");
            logger.error("Error creating school leaver record: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Get school leaver by ID
     * 
     * @param id the school leaver ID
     * @param session the HTTP session
     * @return school leaver data
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSchoolLeaverById(
            @PathVariable Long id,
            HttpSession session) {
        
        logger.info("Getting school leaver by ID: {}", id);
        
        Map<String, Object> response = new HashMap<>();
        
        // Check authentication
        if (!LoginController.isLoggedIn(session)) {
            response.put("error", "Authentication required");
            response.put("message", "Please log in to perform this operation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            SchoolLeaver schoolLeaver = schoolLeaverService.getSchoolLeaverById(id);
            
            response.put("success", true);
            response.put("data", schoolLeaver);
            
            logger.info("School leaver retrieved successfully: {}", id);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Not found");
            response.put("message", "School leaver record not found with ID: " + id);
            logger.warn("School leaver not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    /**
     * Get all school leavers
     * 
     * @param session the HTTP session
     * @return list of all school leavers
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSchoolLeavers(HttpSession session) {
        
        logger.info("Getting all school leavers");
        
        Map<String, Object> response = new HashMap<>();
        
        // Check authentication
        if (!LoginController.isLoggedIn(session)) {
            response.put("error", "Authentication required");
            response.put("message", "Please log in to perform this operation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            List<SchoolLeaver> schoolLeavers = schoolLeaverService.getAllSchoolLeavers();
            
            response.put("success", true);
            response.put("data", schoolLeavers);
            response.put("count", schoolLeavers.size());
            
            logger.info("Retrieved {} school leaver records", schoolLeavers.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Retrieval failed");
            response.put("message", "An error occurred while retrieving school leaver records");
            logger.error("Error retrieving school leavers: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Get school leavers with pagination
     * 
     * @param page page number (0-based)
     * @param size page size
     * @param sortBy sort field
     * @param sortDir sort direction
     * @param session the HTTP session
     * @return paginated school leavers
     */
    @GetMapping("/paginated")
    public ResponseEntity<Map<String, Object>> getSchoolLeaversWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "quarter") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            HttpSession session) {
        
        logger.info("Getting school leavers with pagination - page: {}, size: {}, sort: {} {}", 
                   page, size, sortBy, sortDir);
        
        Map<String, Object> response = new HashMap<>();
        
        // Check authentication
        if (!LoginController.isLoggedIn(session)) {
            response.put("error", "Authentication required");
            response.put("message", "Please log in to perform this operation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            Page<SchoolLeaver> schoolLeaversPage = schoolLeaverService.getSchoolLeaversWithPagination(page, size, sortBy, sortDir);
            
            response.put("success", true);
            response.put("data", schoolLeaversPage.getContent());
            response.put("pagination", Map.of(
                "currentPage", schoolLeaversPage.getNumber(),
                "totalPages", schoolLeaversPage.getTotalPages(),
                "totalElements", schoolLeaversPage.getTotalElements(),
                "size", schoolLeaversPage.getSize(),
                "hasNext", schoolLeaversPage.hasNext(),
                "hasPrevious", schoolLeaversPage.hasPrevious()
            ));
            
            logger.info("Retrieved {} school leaver records (page {} of {})", 
                       schoolLeaversPage.getContent().size(), page + 1, schoolLeaversPage.getTotalPages());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Retrieval failed");
            response.put("message", "An error occurred while retrieving school leaver records");
            logger.error("Error retrieving paginated school leavers: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Update school leaver
     * 
     * @param id the school leaver ID
     * @param schoolLeaverDto the updated school leaver data
     * @param bindingResult the binding result
     * @param session the HTTP session
     * @return updated school leaver
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSchoolLeaver(
            @PathVariable Long id,
            @Valid @RequestBody SchoolLeaverDto schoolLeaverDto,
            BindingResult bindingResult,
            HttpSession session) {
        
        logger.info("Updating school leaver with ID: {}", id);
        
        Map<String, Object> response = new HashMap<>();
        
        // Check authentication
        if (!LoginController.isLoggedIn(session)) {
            response.put("error", "Authentication required");
            response.put("message", "Please log in to perform this operation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        // Validate form data
        if (bindingResult.hasErrors()) {
            response.put("error", "Validation failed");
            response.put("message", "Please correct the errors below");
            response.put("errors", validationService.getValidationErrors(bindingResult));
            logger.warn("School leaver update failed with {} validation errors", bindingResult.getErrorCount());
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            // Sanitize and update school leaver
            SchoolLeaverDto sanitizedDto = validationService.sanitizeSchoolLeaverData(schoolLeaverDto);
            SchoolLeaver updatedSchoolLeaver = schoolLeaverService.updateSchoolLeaver(id, sanitizedDto);
            
            response.put("success", true);
            response.put("message", "School leaver record updated successfully");
            response.put("data", updatedSchoolLeaver);
            
            logger.info("School leaver record updated successfully: {}", id);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Update failed");
            response.put("message", "An error occurred while updating the school leaver record");
            logger.error("Error updating school leaver record {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Delete school leaver
     * 
     * @param id the school leaver ID
     * @param session the HTTP session
     * @return deletion result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSchoolLeaver(
            @PathVariable Long id,
            HttpSession session) {
        
        logger.info("Deleting school leaver with ID: {}", id);
        
        Map<String, Object> response = new HashMap<>();
        
        // Check authentication
        if (!LoginController.isLoggedIn(session)) {
            response.put("error", "Authentication required");
            response.put("message", "Please log in to perform this operation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            schoolLeaverService.deleteSchoolLeaver(id);
            
            response.put("success", true);
            response.put("message", "School leaver record deleted successfully");
            response.put("id", id);
            
            logger.info("School leaver record deleted successfully: {}", id);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Deletion failed");
            response.put("message", "An error occurred while deleting the school leaver record");
            logger.error("Error deleting school leaver record {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Search school leavers
     * 
     * @param searchDto the search criteria
     * @param bindingResult the binding result
     * @param session the HTTP session
     * @return search results
     */
    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> searchSchoolLeavers(
            @Valid @RequestBody SearchDto searchDto,
            BindingResult bindingResult,
            HttpSession session) {
        
        logger.info("Searching school leavers with criteria: {}", searchDto);
        
        Map<String, Object> response = new HashMap<>();
        
        // Check authentication
        if (!LoginController.isLoggedIn(session)) {
            response.put("error", "Authentication required");
            response.put("message", "Please log in to perform this operation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        // Validate search criteria
        if (bindingResult.hasErrors()) {
            response.put("error", "Validation failed");
            response.put("message", "Please correct the search criteria");
            response.put("errors", validationService.getValidationErrors(bindingResult));
            logger.warn("School leaver search failed with {} validation errors", bindingResult.getErrorCount());
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            SearchDto normalizedSearch = searchDto.getNormalizedCriteria();
            
            if (normalizedSearch.hasSearchCriteria()) {
                // Search with criteria
                Page<SchoolLeaver> searchResults = schoolLeaverService.searchSchoolLeaversWithPagination(
                    normalizedSearch.getStatisticCode(),
                    normalizedSearch.getQuarter(),
                    normalizedSearch.getSex(),
                    normalizedSearch.getPage(),
                    normalizedSearch.getSize()
                );
                
                response.put("success", true);
                response.put("data", searchResults.getContent());
                response.put("pagination", Map.of(
                    "currentPage", searchResults.getNumber(),
                    "totalPages", searchResults.getTotalPages(),
                    "totalElements", searchResults.getTotalElements(),
                    "size", searchResults.getSize(),
                    "hasNext", searchResults.hasNext(),
                    "hasPrevious", searchResults.hasPrevious()
                ));
                response.put("searchCriteria", normalizedSearch);
                
                logger.info("Search completed: {} results found", searchResults.getTotalElements());
            } else {
                // No search criteria, return paginated results
                Page<SchoolLeaver> allResults = schoolLeaverService.getSchoolLeaversWithPagination(
                    normalizedSearch.getPage(),
                    normalizedSearch.getSize(),
                    normalizedSearch.getSortBy(),
                    normalizedSearch.getSortDir()
                );
                
                response.put("success", true);
                response.put("data", allResults.getContent());
                response.put("pagination", Map.of(
                    "currentPage", allResults.getNumber(),
                    "totalPages", allResults.getTotalPages(),
                    "totalElements", allResults.getTotalElements(),
                    "size", allResults.getSize(),
                    "hasNext", allResults.hasNext(),
                    "hasPrevious", allResults.hasPrevious()
                ));
                response.put("message", "No search criteria provided, returning all records");
                
                logger.info("No search criteria provided, returned {} records", allResults.getTotalElements());
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Search failed");
            response.put("message", "An error occurred while searching school leaver records");
            logger.error("Error searching school leavers: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Get statistics summary
     * 
     * @param session the HTTP session
     * @return statistics summary
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics(HttpSession session) {
        
        logger.info("Getting statistics summary");
        
        Map<String, Object> response = new HashMap<>();
        
        // Check authentication
        if (!LoginController.isLoggedIn(session)) {
            response.put("error", "Authentication required");
            response.put("message", "Please log in to perform this operation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            Object[] summary = schoolLeaverService.getStatisticsSummary();
            long totalCount = schoolLeaverService.getTotalRecordCount();
            
            response.put("success", true);
            response.put("statistics", Map.of(
                "totalRecords", totalCount,
                "count", summary[0],
                "average", summary[1],
                "maximum", summary[2],
                "minimum", summary[3],
                "sum", summary[4]
            ));
            
            logger.info("Statistics summary retrieved successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Statistics failed");
            response.put("message", "An error occurred while retrieving statistics");
            logger.error("Error retrieving statistics: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Get distinct values for filters
     * 
     * @param session the HTTP session
     * @return distinct values
     */
    @GetMapping("/filters")
    public ResponseEntity<Map<String, Object>> getFilterOptions(HttpSession session) {
        
        logger.info("Getting filter options");
        
        Map<String, Object> response = new HashMap<>();
        
        // Check authentication
        if (!LoginController.isLoggedIn(session)) {
            response.put("error", "Authentication required");
            response.put("message", "Please log in to perform this operation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            response.put("success", true);
            response.put("filters", Map.of(
                "statisticCodes", schoolLeaverService.getDistinctStatisticCodes(),
                "quarters", schoolLeaverService.getDistinctQuarters(),
                "sexes", schoolLeaverService.getDistinctSexes(),
                "units", schoolLeaverService.getDistinctUnits()
            ));
            
            logger.info("Filter options retrieved successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Filter options failed");
            response.put("message", "An error occurred while retrieving filter options");
            logger.error("Error retrieving filter options: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 