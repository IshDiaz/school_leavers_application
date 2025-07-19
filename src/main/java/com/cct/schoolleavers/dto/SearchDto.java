package com.cct.schoolleavers.dto;

import jakarta.validation.constraints.*;

/**
 * Search Data Transfer Object
 * 
 * This DTO is used for form validation and data transfer between layers.
 * Contains simple validation annotations for search form validation.
 * 
 * @author CCT Student
 * @version 1.0
 */
public class SearchDto {
    
    @Size(max = 20, message = "Statistic code must not exceed 20 characters")
    private String statisticCode;
    
    @Size(max = 6, message = "Quarter must not exceed 6 characters")
    private String quarter;
    
    @Size(max = 20, message = "Sex must not exceed 20 characters")
    private String sex;
    
    @Min(value = 0, message = "Page number must be 0 or greater")
    private Integer page = 0;
    
    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private Integer size = 10;
    
    private String sortDir = "desc";
    
    private String sortBy = "quarter";
    
    // Default constructor
    public SearchDto() {}
    
    // Constructor with search criteria
    public SearchDto(String statisticCode, String quarter, String sex) {
        this.statisticCode = statisticCode;
        this.quarter = quarter;
        this.sex = sex;
    }
    
    // Constructor with all fields
    public SearchDto(String statisticCode, String quarter, String sex, 
                    Integer page, Integer size, String sortDir, String sortBy) {
        this.statisticCode = statisticCode;
        this.quarter = quarter;
        this.sex = sex;
        this.page = page != null ? page : 0;
        this.size = size != null ? size : 10;
        this.sortDir = sortDir != null ? sortDir : "desc";
        this.sortBy = sortBy != null ? sortBy : "quarter";
    }
    
    // Getters and Setters
    public String getStatisticCode() {
        return statisticCode;
    }
    
    public void setStatisticCode(String statisticCode) {
        this.statisticCode = statisticCode;
    }
    
    public String getQuarter() {
        return quarter;
    }
    
    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public Integer getPage() {
        return page != null ? page : 0;
    }
    
    public void setPage(Integer page) {
        this.page = page != null ? page : 0;
    }
    
    public Integer getSize() {
        return size != null ? size : 10;
    }
    
    public void setSize(Integer size) {
        this.size = size != null ? size : 10;
    }
    
    public String getSortDir() {
        return sortDir != null ? sortDir : "desc";
    }
    
    public void setSortDir(String sortDir) {
        this.sortDir = sortDir != null ? sortDir : "desc";
    }
    
    public String getSortBy() {
        return sortBy != null ? sortBy : "quarter";
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy != null ? sortBy : "quarter";
    }
    
    /**
     * Check if any search criteria is provided
     * 
     * @return true if at least one search criteria is provided
     */
    public boolean hasSearchCriteria() {
        return (statisticCode != null && !statisticCode.trim().isEmpty()) ||
               (quarter != null && !quarter.trim().isEmpty()) ||
               (sex != null && !sex.trim().isEmpty());
    }
    
    /**
     * Get normalized search criteria (trimmed and uppercase where appropriate)
     * 
     * @return normalized search criteria
     */
    public SearchDto getNormalizedCriteria() {
        SearchDto normalized = new SearchDto();
        normalized.statisticCode = statisticCode != null ? statisticCode.trim().toUpperCase() : null;
        normalized.quarter = quarter != null ? quarter.trim() : null;
        normalized.sex = sex != null ? sex.trim() : null;
        normalized.page = getPage();
        normalized.size = getSize();
        normalized.sortDir = getSortDir();
        normalized.sortBy = getSortBy();
        return normalized;
    }
    
    @Override
    public String toString() {
        return "SearchDto{" +
                "statisticCode='" + statisticCode + '\'' +
                ", quarter='" + quarter + '\'' +
                ", sex='" + sex + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", sortDir='" + sortDir + '\'' +
                ", sortBy='" + sortBy + '\'' +
                '}';
    }
} 