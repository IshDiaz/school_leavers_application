package com.cct.schoolleavers.services;

import com.cct.schoolleavers.dto.SchoolLeaverDto;
import com.cct.schoolleavers.entities.SchoolLeaver;
import com.cct.schoolleavers.repositories.SchoolLeaverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * School Leaver Service
 * 
 * Simple service with basic CRUD operations.
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
     * Create a new school leaver
     * 
     * @param schoolLeaverDto the school leaver data
     * @return created school leaver
     */
    public SchoolLeaver createSchoolLeaver(SchoolLeaverDto schoolLeaverDto) {
        logger.info("Creating new school leaver record");
        
        SchoolLeaver schoolLeaver = new SchoolLeaver();
        schoolLeaver.setStatisticCode(schoolLeaverDto.getStatisticCode());
        schoolLeaver.setStatisticLabel(schoolLeaverDto.getStatisticLabel());
        schoolLeaver.setQuarter(schoolLeaverDto.getQuarter());
        schoolLeaver.setSex(schoolLeaverDto.getSex());
        schoolLeaver.setUnit(schoolLeaverDto.getUnit());
        schoolLeaver.setValue(schoolLeaverDto.getValue());
        
        SchoolLeaver saved = schoolLeaverRepository.save(schoolLeaver);
        logger.info("School leaver created with ID: {}", saved.getId());
        
        return saved;
    }
    
    /**
     * Get school leaver by ID
     * 
     * @param id the school leaver ID
     * @return school leaver if found
     */
    public SchoolLeaver getSchoolLeaverById(Long id) {
        logger.info("Getting school leaver with ID: {}", id);
        
        Optional<SchoolLeaver> optional = schoolLeaverRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("School leaver not found with ID: " + id);
        }
    }
    
    /**
     * Get all school leavers
     * 
     * @return list of all school leavers
     */
    public List<SchoolLeaver> getAllSchoolLeavers() {
        logger.info("Getting all school leavers");
        return schoolLeaverRepository.findAll();
    }
    
    /**
     * Update school leaver
     * 
     * @param id the school leaver ID
     * @param schoolLeaverDto the updated data
     * @return updated school leaver
     */
    public SchoolLeaver updateSchoolLeaver(Long id, SchoolLeaverDto schoolLeaverDto) {
        logger.info("Updating school leaver with ID: {}", id);
        
        SchoolLeaver existing = getSchoolLeaverById(id);
        
        existing.setStatisticCode(schoolLeaverDto.getStatisticCode());
        existing.setStatisticLabel(schoolLeaverDto.getStatisticLabel());
        existing.setQuarter(schoolLeaverDto.getQuarter());
        existing.setSex(schoolLeaverDto.getSex());
        existing.setUnit(schoolLeaverDto.getUnit());
        existing.setValue(schoolLeaverDto.getValue());
        
        SchoolLeaver updated = schoolLeaverRepository.save(existing);
        logger.info("School leaver updated with ID: {}", id);
        
        return updated;
    }
    
    /**
     * Delete school leaver
     * 
     * @param id the school leaver ID
     */
    public void deleteSchoolLeaver(Long id) {
        logger.info("Deleting school leaver with ID: {}", id);
        
        SchoolLeaver schoolLeaver = getSchoolLeaverById(id);
        schoolLeaverRepository.delete(schoolLeaver);
        
        logger.info("School leaver deleted with ID: {}", id);
    }
} 