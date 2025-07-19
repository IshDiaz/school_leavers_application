package com.cct.schoolleavers.services;

import com.cct.schoolleavers.dto.SchoolLeaverDto;
import com.cct.schoolleavers.entities.SchoolLeaver;
import com.cct.schoolleavers.exception.ResourceNotFoundException;
import com.cct.schoolleavers.repositories.SchoolLeaverRepository;
import com.cct.schoolleavers.util.Constants;
import com.cct.schoolleavers.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * School Leaver Service
 * 
 * This service provides business logic for CRUD operations on school leavers data.
 * It includes validation, search functionality, and statistical analysis.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Service
public class SchoolLeaverService {
    
    private static final Logger logger = LoggerFactory.getLogger(SchoolLeaverService.class);
    
    @Autowired
    private SchoolLeaverRepository schoolLeaverRepository;
    
    /**
     * Create a new school leaver record
     * 
     * @param schoolLeaverDto the school leaver data
     * @return created school leaver
     */
    public SchoolLeaver createSchoolLeaver(SchoolLeaverDto schoolLeaverDto) {
        try {
            logger.info("Creating new school leaver record: {}", schoolLeaverDto.getStatisticCode());
            
            // Validate input
            validateSchoolLeaverData(schoolLeaverDto);
            
            // Create new school leaver
            SchoolLeaver schoolLeaver = new SchoolLeaver();
            schoolLeaver.setStatisticCode(schoolLeaverDto.getStatisticCode().toUpperCase());
            schoolLeaver.setStatisticLabel(schoolLeaverDto.getStatisticLabel());
            schoolLeaver.setQuarter(schoolLeaverDto.getQuarter());
            schoolLeaver.setSex(schoolLeaverDto.getSex());
            schoolLeaver.setUnit(schoolLeaverDto.getUnit());
            schoolLeaver.setValue(schoolLeaverDto.getValue());
            
            SchoolLeaver savedSchoolLeaver = schoolLeaverRepository.save(schoolLeaver);
            logger.info("School leaver record created successfully with ID: {}", savedSchoolLeaver.getId());
            
            return savedSchoolLeaver;
            
        } catch (Exception e) {
            logger.error("Error creating school leaver record: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get school leaver by ID
     * 
     * @param id the school leaver ID
     * @return school leaver if found
     * @throws ResourceNotFoundException if school leaver not found
     */
    public SchoolLeaver getSchoolLeaverById(Long id) {
        try {
            Optional<SchoolLeaver> schoolLeaverOptional = schoolLeaverRepository.findById(id);
            if (schoolLeaverOptional.isPresent()) {
                return schoolLeaverOptional.get();
            } else {
                throw new ResourceNotFoundException("School leaver not found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error getting school leaver by ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get all school leavers
     * 
     * @return list of all school leavers
     */
    public List<SchoolLeaver> getAllSchoolLeavers() {
        try {
            return schoolLeaverRepository.findAll();
        } catch (Exception e) {
            logger.error("Error getting all school leavers: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get school leavers with pagination
     * 
     * @param page page number (0-based)
     * @param size page size
     * @param sortBy sort field
     * @param sortDir sort direction
     * @return page of school leavers
     */
    public Page<SchoolLeaver> getSchoolLeaversWithPagination(int page, int size, String sortBy, String sortDir) {
        try {
            // Validate pagination parameters
            page = Math.max(0, page);
            size = Math.min(Math.max(1, size), Constants.MAX_PAGE_SIZE);
            
            // Create sort
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            
            // Create pageable
            Pageable pageable = PageRequest.of(page, size, sort);
            
            return schoolLeaverRepository.findAll(pageable);
            
        } catch (Exception e) {
            logger.error("Error getting school leavers with pagination: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Update school leaver
     * 
     * @param id the school leaver ID
     * @param schoolLeaverDto the updated school leaver data
     * @return updated school leaver
     * @throws ResourceNotFoundException if school leaver not found
     */
    public SchoolLeaver updateSchoolLeaver(Long id, SchoolLeaverDto schoolLeaverDto) {
        try {
            logger.info("Updating school leaver with ID: {}", id);
            
            SchoolLeaver existingSchoolLeaver = getSchoolLeaverById(id);
            
            // Validate input
            validateSchoolLeaverData(schoolLeaverDto);
            
            // Update fields
            existingSchoolLeaver.setStatisticCode(schoolLeaverDto.getStatisticCode().toUpperCase());
            existingSchoolLeaver.setStatisticLabel(schoolLeaverDto.getStatisticLabel());
            existingSchoolLeaver.setQuarter(schoolLeaverDto.getQuarter());
            existingSchoolLeaver.setSex(schoolLeaverDto.getSex());
            existingSchoolLeaver.setUnit(schoolLeaverDto.getUnit());
            existingSchoolLeaver.setValue(schoolLeaverDto.getValue());
            
            SchoolLeaver updatedSchoolLeaver = schoolLeaverRepository.save(existingSchoolLeaver);
            logger.info("School leaver updated successfully with ID: {}", updatedSchoolLeaver.getId());
            
            return updatedSchoolLeaver;
            
        } catch (Exception e) {
            logger.error("Error updating school leaver with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Delete school leaver
     * 
     * @param id the school leaver ID
     * @throws ResourceNotFoundException if school leaver not found
     */
    public void deleteSchoolLeaver(Long id) {
        try {
            logger.info("Deleting school leaver with ID: {}", id);
            
            SchoolLeaver schoolLeaver = getSchoolLeaverById(id);
            schoolLeaverRepository.delete(schoolLeaver);
            
            logger.info("School leaver deleted successfully with ID: {}", id);
            
        } catch (Exception e) {
            logger.error("Error deleting school leaver with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Search school leavers by criteria
     * 
     * @param statisticCode the statistic code pattern
     * @param quarter the quarter
     * @param sex the sex
     * @return list of matching school leavers
     */
    public List<SchoolLeaver> searchSchoolLeavers(String statisticCode, String quarter, String sex) {
        try {
            logger.info("Searching school leavers - Code: {}, Quarter: {}, Sex: {}", statisticCode, quarter, sex);
            
            return schoolLeaverRepository.findWithCriteria(statisticCode, quarter, sex);
            
        } catch (Exception e) {
            logger.error("Error searching school leavers: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Search school leavers with pagination
     * 
     * @param statisticCode the statistic code pattern
     * @param quarter the quarter
     * @param sex the sex
     * @param page page number (0-based)
     * @param size page size
     * @return page of matching school leavers
     */
    public Page<SchoolLeaver> searchSchoolLeaversWithPagination(String statisticCode, String quarter, String sex, 
                                                               int page, int size) {
        try {
            // Validate pagination parameters
            page = Math.max(0, page);
            size = Math.min(Math.max(1, size), Constants.MAX_PAGE_SIZE);
            
            // Create pageable
            Pageable pageable = PageRequest.of(page, size, Sort.by("quarter").descending().and(Sort.by("statisticCode").ascending()));
            
            return schoolLeaverRepository.findWithCriteria(statisticCode, quarter, sex, pageable);
            
        } catch (Exception e) {
            logger.error("Error searching school leavers with pagination: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get school leavers by statistic code
     * 
     * @param statisticCode the statistic code
     * @return list of school leavers with the specified statistic code
     */
    public List<SchoolLeaver> getSchoolLeaversByStatisticCode(String statisticCode) {
        try {
            return schoolLeaverRepository.findByStatisticCode(statisticCode.toUpperCase());
        } catch (Exception e) {
            logger.error("Error getting school leavers by statistic code {}: {}", statisticCode, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get school leavers by quarter
     * 
     * @param quarter the quarter
     * @return list of school leavers for the specified quarter
     */
    public List<SchoolLeaver> getSchoolLeaversByQuarter(String quarter) {
        try {
            return schoolLeaverRepository.findByQuarter(quarter);
        } catch (Exception e) {
            logger.error("Error getting school leavers by quarter {}: {}", quarter, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get school leavers by sex
     * 
     * @param sex the sex
     * @return list of school leavers for the specified sex
     */
    public List<SchoolLeaver> getSchoolLeaversBySex(String sex) {
        try {
            return schoolLeaverRepository.findBySex(sex);
        } catch (Exception e) {
            logger.error("Error getting school leavers by sex {}: {}", sex, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get school leavers by value range
     * 
     * @param minValue minimum value
     * @param maxValue maximum value
     * @return list of school leavers within the value range
     */
    public List<SchoolLeaver> getSchoolLeaversByValueRange(BigDecimal minValue, BigDecimal maxValue) {
        try {
            return schoolLeaverRepository.findByValueBetween(minValue, maxValue);
        } catch (Exception e) {
            logger.error("Error getting school leavers by value range {}-{}: {}", minValue, maxValue, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get distinct statistic codes
     * 
     * @return list of distinct statistic codes
     */
    public List<String> getDistinctStatisticCodes() {
        try {
            return schoolLeaverRepository.findDistinctStatisticCodes();
        } catch (Exception e) {
            logger.error("Error getting distinct statistic codes: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get distinct quarters
     * 
     * @return list of distinct quarters
     */
    public List<String> getDistinctQuarters() {
        try {
            return schoolLeaverRepository.findDistinctQuarters();
        } catch (Exception e) {
            logger.error("Error getting distinct quarters: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get distinct sexes
     * 
     * @return list of distinct sexes
     */
    public List<String> getDistinctSexes() {
        try {
            return schoolLeaverRepository.findDistinctSexes();
        } catch (Exception e) {
            logger.error("Error getting distinct sexes: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get distinct units
     * 
     * @return list of distinct units
     */
    public List<String> getDistinctUnits() {
        try {
            return schoolLeaverRepository.findDistinctUnits();
        } catch (Exception e) {
            logger.error("Error getting distinct units: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Get average value by statistic code
     * 
     * @param statisticCode the statistic code
     * @return average value
     */
    public BigDecimal getAverageValueByStatisticCode(String statisticCode) {
        try {
            BigDecimal average = schoolLeaverRepository.getAverageValueByStatisticCode(statisticCode.toUpperCase());
            return average != null ? average : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.error("Error getting average value by statistic code {}: {}", statisticCode, e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * Get average value by quarter
     * 
     * @param quarter the quarter
     * @return average value
     */
    public BigDecimal getAverageValueByQuarter(String quarter) {
        try {
            BigDecimal average = schoolLeaverRepository.getAverageValueByQuarter(quarter);
            return average != null ? average : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.error("Error getting average value by quarter {}: {}", quarter, e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * Get maximum value by statistic code
     * 
     * @param statisticCode the statistic code
     * @return maximum value
     */
    public BigDecimal getMaxValueByStatisticCode(String statisticCode) {
        try {
            BigDecimal max = schoolLeaverRepository.getMaxValueByStatisticCode(statisticCode.toUpperCase());
            return max != null ? max : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.error("Error getting max value by statistic code {}: {}", statisticCode, e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * Get minimum value by statistic code
     * 
     * @param statistic code
     * @return minimum value
     */
    public BigDecimal getMinValueByStatisticCode(String statisticCode) {
        try {
            BigDecimal min = schoolLeaverRepository.getMinValueByStatisticCode(statisticCode.toUpperCase());
            return min != null ? min : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.error("Error getting min value by statistic code {}: {}", statisticCode, e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * Get sum by statistic code
     * 
     * @param statisticCode the statistic code
     * @return sum of values
     */
    public BigDecimal getSumByStatisticCode(String statisticCode) {
        try {
            BigDecimal sum = schoolLeaverRepository.getSumByStatisticCode(statisticCode.toUpperCase());
            return sum != null ? sum : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.error("Error getting sum by statistic code {}: {}", statisticCode, e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * Get total record count
     * 
     * @return total number of records
     */
    public long getTotalRecordCount() {
        try {
            return schoolLeaverRepository.count();
        } catch (Exception e) {
            logger.error("Error getting total record count: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    /**
     * Get statistics summary
     * 
     * @return object array with summary statistics [count, avg, max, min, sum]
     */
    public Object[] getStatisticsSummary() {
        try {
            return schoolLeaverRepository.getStatisticsSummary();
        } catch (Exception e) {
            logger.error("Error getting statistics summary: {}", e.getMessage(), e);
            return new Object[]{0L, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
        }
    }
    
    /**
     * Validate school leaver data
     * 
     * @param schoolLeaverDto the school leaver data to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateSchoolLeaverData(SchoolLeaverDto schoolLeaverDto) {
        // Validate statistic code
        if (!ValidationUtil.isNotEmpty(schoolLeaverDto.getStatisticCode()) ||
            !ValidationUtil.isValidLength(schoolLeaverDto.getStatisticCode(), 1, Constants.MAX_STATISTIC_CODE_LENGTH) ||
            !ValidationUtil.isValidStatisticCode(schoolLeaverDto.getStatisticCode())) {
            throw new IllegalArgumentException("Invalid statistic code");
        }
        
        // Validate statistic label
        if (!ValidationUtil.isNotEmpty(schoolLeaverDto.getStatisticLabel()) ||
            !ValidationUtil.isValidLength(schoolLeaverDto.getStatisticLabel(), 1, Constants.MAX_STATISTIC_LABEL_LENGTH)) {
            throw new IllegalArgumentException("Invalid statistic label");
        }
        
        // Validate quarter
        if (!ValidationUtil.isNotEmpty(schoolLeaverDto.getQuarter()) ||
            !ValidationUtil.isValidQuarter(schoolLeaverDto.getQuarter())) {
            throw new IllegalArgumentException("Invalid quarter format. Must be Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY");
        }
        
        // Validate sex
        if (!ValidationUtil.isNotEmpty(schoolLeaverDto.getSex()) ||
            !ValidationUtil.isValidLength(schoolLeaverDto.getSex(), 1, Constants.MAX_SEX_LENGTH)) {
            throw new IllegalArgumentException("Invalid sex");
        }
        
        // Validate unit
        if (!ValidationUtil.isNotEmpty(schoolLeaverDto.getUnit()) ||
            !ValidationUtil.isValidLength(schoolLeaverDto.getUnit(), 1, Constants.MAX_UNIT_LENGTH)) {
            throw new IllegalArgumentException("Invalid unit");
        }
        
        // Validate value
        if (schoolLeaverDto.getValue() == null || !ValidationUtil.isValidValue(schoolLeaverDto.getValue())) {
            throw new IllegalArgumentException("Invalid value. Must be between 0 and 999.99");
        }
    }
} 