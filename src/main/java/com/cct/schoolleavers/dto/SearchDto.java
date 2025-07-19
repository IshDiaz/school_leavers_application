package com.cct.schoolleavers.dto;

import jakarta.validation.constraints.*;

/**
 * Search Data Transfer Object
 * 
 * This DTO is used for form validation and data transfer between layers.
 * Contains validation annotations for search form validation.
 * 
 * @author CCT Student
 * @version 1.0
 */
public class SearchDto {
    
    @Size(max = 20, message = "Statistic code must not exceed 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Statistic code can only contain letters and numbers")
    private String statisticCode;
    
    @Size(max = 6, message = "Quarter must not exceed 6 characters")
    @Pattern(regexp = "^Q[1-4]\\d{4}$", message = "Quarter must be in format Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY")
    private String quarter;
    
    @Size(max = 20, message = "Sex must not exceed 20 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Sex can only contain letters and spaces")
    private String sex;
    
    @Min(value = 0, message = "Page number must be 0 or greater")
    private Integer page = 0;
    
    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private Integer size = 10;
    
    @Pattern(regexp = "^(asc|desc)$", message = "Sort direction must be 'asc' or 'desc'")
    private String sortDir = "desc";
    
    @Size(max = 20, message = "Sort field must not exceed 20 characters")
    @Pattern(regexp = "^[a-zA-Z_]*$", message = "Sort field can only contain letters and underscores")
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