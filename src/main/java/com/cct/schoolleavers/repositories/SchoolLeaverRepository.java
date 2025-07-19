package com.cct.schoolleavers.repositories;

import com.cct.schoolleavers.entities.SchoolLeaver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * School Leaver Repository Interface
 * 
 * This interface provides data access methods for the SchoolLeaver entity.
 * It extends JpaRepository to inherit basic CRUD operations and adds
 * custom query methods for statistical analysis.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Repository
public interface SchoolLeaverRepository extends JpaRepository<SchoolLeaver, Long> {
    
    /**
     * Find school leavers by statistic code
     * 
     * @param statisticCode the statistic code to search for
     * @return List of school leavers with the specified statistic code
     */
    List<SchoolLeaver> findByStatisticCode(String statisticCode);
    
    /**
     * Find school leavers by quarter
     * 
     * @param quarter the quarter to search for
     * @return List of school leavers for the specified quarter
     */
    List<SchoolLeaver> findByQuarter(String quarter);
    
    /**
     * Find school leavers by sex
     * 
     * @param sex the sex to search for
     * @return List of school leavers for the specified sex
     */
    List<SchoolLeaver> findBySex(String sex);
    
    /**
     * Find school leavers by unit
     * 
     * @param unit the unit to search for
     * @return List of school leavers for the specified unit
     */
    List<SchoolLeaver> findByUnit(String unit);
    
    /**
     * Find school leavers by statistic code and quarter
     * 
     * @param statisticCode the statistic code
     * @param quarter the quarter
     * @return List of school leavers matching both criteria
     */
    List<SchoolLeaver> findByStatisticCodeAndQuarter(String statisticCode, String quarter);
    
    /**
     * Find school leavers by statistic code and sex
     * 
     * @param statisticCode the statistic code
     * @param sex the sex
     * @return List of school leavers matching both criteria
     */
    List<SchoolLeaver> findByStatisticCodeAndSex(String statisticCode, String sex);
    
    /**
     * Find school leavers by quarter and sex
     * 
     * @param quarter the quarter
     * @param sex the sex
     * @return List of school leavers matching both criteria
     */
    List<SchoolLeaver> findByQuarterAndSex(String quarter, String sex);
    
    /**
     * Find school leavers with value greater than specified amount
     * 
     * @param value the minimum value
     * @return List of school leavers with value greater than specified
     */
    List<SchoolLeaver> findByValueGreaterThan(BigDecimal value);
    
    /**
     * Find school leavers with value less than specified amount
     * 
     * @param value the maximum value
     * @return List of school leavers with value less than specified
     */
    List<SchoolLeaver> findByValueLessThan(BigDecimal value);
    
    /**
     * Find school leavers with value between specified range
     * 
     * @param minValue the minimum value
     * @param maxValue the maximum value
     * @return List of school leavers within the value range
     */
    List<SchoolLeaver> findByValueBetween(BigDecimal minValue, BigDecimal maxValue);
    
    /**
     * Find school leavers by statistic label containing pattern (case-insensitive)
     * 
     * @param labelPattern the label pattern to search for
     * @return List of school leavers matching the pattern
     */
    List<SchoolLeaver> findByStatisticLabelContainingIgnoreCase(String labelPattern);
    
    /**
     * Find school leavers by statistic code containing pattern (case-insensitive)
     * 
     * @param codePattern the code pattern to search for
     * @return List of school leavers matching the pattern
     */
    List<SchoolLeaver> findByStatisticCodeContainingIgnoreCase(String codePattern);
    
    /**
     * Find school leavers created after a specific date
     * 
     * @param date the date to filter by
     * @return List of school leavers created after the date
     */
    List<SchoolLeaver> findByCreatedAtAfter(LocalDateTime date);
    
    /**
     * Find school leavers updated after a specific date
     * 
     * @param date the date to filter by
     * @return List of school leavers updated after the date
     */
    List<SchoolLeaver> findByUpdatedAtAfter(LocalDateTime date);
    
    /**
     * Count school leavers by statistic code
     * 
     * @param statisticCode the statistic code
     * @return count of school leavers with the specified statistic code
     */
    long countByStatisticCode(String statisticCode);
    
    /**
     * Count school leavers by quarter
     * 
     * @param quarter the quarter
     * @return count of school leavers for the specified quarter
     */
    long countByQuarter(String quarter);
    
    /**
     * Count school leavers by sex
     * 
     * @param sex the sex
     * @return count of school leavers for the specified sex
     */
    long countBySex(String sex);
    
    /**
     * Count school leavers by unit
     * 
     * @param unit the unit
     * @return count of school leavers for the specified unit
     */
    long countByUnit(String unit);
    
    /**
     * Find school leavers with pagination
     * 
     * @param pageable pagination information
     * @return Page of school leavers
     */
    Page<SchoolLeaver> findAll(Pageable pageable);
    
    /**
     * Find school leavers by statistic code with pagination
     * 
     * @param statisticCode the statistic code
     * @param pageable pagination information
     * @return Page of school leavers
     */
    Page<SchoolLeaver> findByStatisticCode(String statisticCode, Pageable pageable);
    
    /**
     * Find school leavers by quarter with pagination
     * 
     * @param quarter the quarter
     * @param pageable pagination information
     * @return Page of school leavers
     */
    Page<SchoolLeaver> findByQuarter(String quarter, Pageable pageable);
    
    /**
     * Find school leavers by sex with pagination
     * 
     * @param sex the sex
     * @param pageable pagination information
     * @return Page of school leavers
     */
    Page<SchoolLeaver> findBySex(String sex, Pageable pageable);
    
    // Custom JPQL Queries
    
    /**
     * Find school leavers with custom search criteria
     * 
     * @param statisticCode the statistic code pattern
     * @param quarter the quarter
     * @param sex the sex
     * @return List of school leavers matching the criteria
     */
    @Query("SELECT sl FROM SchoolLeaver sl WHERE " +
           "(:statisticCode IS NULL OR sl.statisticCode LIKE %:statisticCode%) AND " +
           "(:quarter IS NULL OR sl.quarter = :quarter) AND " +
           "(:sex IS NULL OR sl.sex = :sex) " +
           "ORDER BY sl.quarter DESC, sl.statisticCode ASC")
    List<SchoolLeaver> findWithCriteria(@Param("statisticCode") String statisticCode,
                                       @Param("quarter") String quarter,
                                       @Param("sex") String sex);
    
    /**
     * Find school leavers with custom search criteria and pagination
     * 
     * @param statisticCode the statistic code pattern
     * @param quarter the quarter
     * @param sex the sex
     * @param pageable pagination information
     * @return Page of school leavers matching the criteria
     */
    @Query("SELECT sl FROM SchoolLeaver sl WHERE " +
           "(:statisticCode IS NULL OR sl.statisticCode LIKE %:statisticCode%) AND " +
           "(:quarter IS NULL OR sl.quarter = :quarter) AND " +
           "(:sex IS NULL OR sl.sex = :sex) " +
           "ORDER BY sl.quarter DESC, sl.statisticCode ASC")
    Page<SchoolLeaver> findWithCriteria(@Param("statisticCode") String statisticCode,
                                       @Param("quarter") String quarter,
                                       @Param("sex") String sex,
                                       Pageable pageable);
    
    /**
     * Calculate average value by statistic code
     * 
     * @param statisticCode the statistic code
     * @return average value for the specified statistic code
     */
    @Query("SELECT AVG(sl.value) FROM SchoolLeaver sl WHERE sl.statisticCode = :statisticCode")
    BigDecimal getAverageValueByStatisticCode(@Param("statisticCode") String statisticCode);
    
    /**
     * Calculate average value by quarter
     * 
     * @param quarter the quarter
     * @return average value for the specified quarter
     */
    @Query("SELECT AVG(sl.value) FROM SchoolLeaver sl WHERE sl.quarter = :quarter")
    BigDecimal getAverageValueByQuarter(@Param("quarter") String quarter);
    
    /**
     * Calculate average value by sex
     * 
     * @param sex the sex
     * @return average value for the specified sex
     */
    @Query("SELECT AVG(sl.value) FROM SchoolLeaver sl WHERE sl.sex = :sex")
    BigDecimal getAverageValueBySex(@Param("sex") String sex);
    
    /**
     * Get maximum value by statistic code
     * 
     * @param statisticCode the statistic code
     * @return maximum value for the specified statistic code
     */
    @Query("SELECT MAX(sl.value) FROM SchoolLeaver sl WHERE sl.statisticCode = :statisticCode")
    BigDecimal getMaxValueByStatisticCode(@Param("statisticCode") String statisticCode);
    
    /**
     * Get minimum value by statistic code
     * 
     * @param statisticCode the statistic code
     * @return minimum value for the specified statistic code
     */
    @Query("SELECT MIN(sl.value) FROM SchoolLeaver sl WHERE sl.statisticCode = :statisticCode")
    BigDecimal getMinValueByStatisticCode(@Param("statisticCode") String statisticCode);
    
    /**
     * Get sum of values by statistic code
     * 
     * @param statisticCode the statistic code
     * @return sum of values for the specified statistic code
     */
    @Query("SELECT SUM(sl.value) FROM SchoolLeaver sl WHERE sl.statisticCode = :statisticCode")
    BigDecimal getSumByStatisticCode(@Param("statisticCode") String statisticCode);
    
    /**
     * Get distinct statistic codes
     * 
     * @return List of distinct statistic codes
     */
    @Query("SELECT DISTINCT sl.statisticCode FROM SchoolLeaver sl ORDER BY sl.statisticCode")
    List<String> findDistinctStatisticCodes();
    
    /**
     * Get distinct quarters
     * 
     * @return List of distinct quarters
     */
    @Query("SELECT DISTINCT sl.quarter FROM SchoolLeaver sl ORDER BY sl.quarter DESC")
    List<String> findDistinctQuarters();
    
    /**
     * Get distinct sexes
     * 
     * @return List of distinct sexes
     */
    @Query("SELECT DISTINCT sl.sex FROM SchoolLeaver sl ORDER BY sl.sex")
    List<String> findDistinctSexes();
    
    /**
     * Get distinct units
     * 
     * @return List of distinct units
     */
    @Query("SELECT DISTINCT sl.unit FROM SchoolLeaver sl ORDER BY sl.unit")
    List<String> findDistinctUnits();
    
    /**
     * Find school leavers by year (extracted from quarter)
     * 
     * @param year the year to search for
     * @return List of school leavers for the specified year
     */
    @Query("SELECT sl FROM SchoolLeaver sl WHERE sl.quarter LIKE %:year% ORDER BY sl.quarter DESC")
    List<SchoolLeaver> findByYear(@Param("year") String year);
    
    /**
     * Get statistics summary
     * 
     * @return Object array with summary statistics
     */
    @Query("SELECT COUNT(sl), AVG(sl.value), MAX(sl.value), MIN(sl.value), SUM(sl.value) FROM SchoolLeaver sl")
    Object[] getStatisticsSummary();
    
    /**
     * Delete school leavers by statistic code
     * 
     * @param statisticCode the statistic code
     */
    void deleteByStatisticCode(String statisticCode);
    
    /**
     * Delete school leavers by quarter
     * 
     * @param quarter the quarter
     */
    void deleteByQuarter(String quarter);
    
    /**
     * Delete school leavers by sex
     * 
     * @param sex the sex
     */
    void deleteBySex(String sex);
} 