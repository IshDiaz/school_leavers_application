package com.cct.schoolleavers.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Data Transfer Object for School Leaver data
 * 
 * This class is used for form handling and data transfer between
 * the presentation layer and service layer.
 * 
 * @author CCT Student
 * @version 1.0
 */
public class SchoolLeaverDto {
    
    private Long id;
    
    @NotBlank(message = "Statistic code is required")
    @Size(max = 20, message = "Statistic code must not exceed 20 characters")
    private String statisticCode;
    
    @NotBlank(message = "Statistic label is required")
    @Size(max = 100, message = "Statistic label must not exceed 100 characters")
    private String statisticLabel;
    
    @NotBlank(message = "Quarter is required")
    @Pattern(regexp = "^[Q][1-4]\\d{4}$", message = "Quarter must be in format Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY")
    private String quarter;
    
    @NotBlank(message = "Sex is required")
    @Size(max = 20, message = "Sex must not exceed 20 characters")
    private String sex;
    
    @NotBlank(message = "Unit is required")
    @Size(max = 10, message = "Unit must not exceed 10 characters")
    private String unit;
    
    @NotNull(message = "Value is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Value must be greater than or equal to 0")
    @DecimalMax(value = "999.99", inclusive = true, message = "Value must be less than or equal to 999.99")
    @Digits(integer = 3, fraction = 2, message = "Value must have up to 3 digits before decimal and 2 after")
    private BigDecimal value;
    
    // Default constructor
    public SchoolLeaverDto() {}
    
    // Constructor with all fields
    public SchoolLeaverDto(Long id, String statisticCode, String statisticLabel, 
                          String quarter, String sex, String unit, BigDecimal value) {
        this.id = id;
        this.statisticCode = statisticCode;
        this.statisticLabel = statisticLabel;
        this.quarter = quarter;
        this.sex = sex;
        this.unit = unit;
        this.value = value;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStatisticCode() {
        return statisticCode;
    }
    
    public void setStatisticCode(String statisticCode) {
        this.statisticCode = statisticCode;
    }
    
    public String getStatisticLabel() {
        return statisticLabel;
    }
    
    public void setStatisticLabel(String statisticLabel) {
        this.statisticLabel = statisticLabel;
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
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public BigDecimal getValue() {
        return value;
    }
    
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "SchoolLeaverDto{" +
                "id=" + id +
                ", statisticCode='" + statisticCode + '\'' +
                ", statisticLabel='" + statisticLabel + '\'' +
                ", quarter='" + quarter + '\'' +
                ", sex='" + sex + '\'' +
                ", unit='" + unit + '\'' +
                ", value=" + value +
                '}';
    }
} 