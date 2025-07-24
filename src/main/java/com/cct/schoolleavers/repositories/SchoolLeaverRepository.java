package com.cct.schoolleavers.repositories;

import com.cct.schoolleavers.entities.SchoolLeaver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * School Leaver Repository Interface
 * 
 * Simple repository with basic CRUD operations.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Repository
public interface SchoolLeaverRepository extends JpaRepository<SchoolLeaver, Long> {
    // Basic CRUD operations are inherited from JpaRepository
} 