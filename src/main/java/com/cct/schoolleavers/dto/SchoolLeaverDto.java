package com.cct.schoolleavers.dto;

import com.cct.schoolleavers.validation.ValidQuarter;
import com.cct.schoolleavers.validation.ValidStatisticCode;
import com.cct.schoolleavers.validation.ValidValue;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * School Leaver Data Transfer Object
 * 
 * This DTO is used for form validation and data transfer between layers.
 * Contains comprehensive validation annotations for backend form validation.
 * 
 * @author CCT Student
 * @version 1.0
 */
public class SchoolLeaverDto {
    
    private Long id;
    
    @NotBlank(message = "Statistic code is required")
    @Size(min = 1, max = 20, message = "Statistic code must be between 1 and 20 characters")
    @ValidStatisticCode(message = "Statistic code must contain only letters and numbers")
    private String statisticCode;
    
    @NotBlank(message = "Statistic label is required")
    @Size(min = 1, max = 100, message = "Statistic label must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-\\(\\)\\,\\.]+$", 
             message = "Statistic label can only contain letters, numbers, spaces, hyphens, parentheses, and commas")
    private String statisticLabel;
    
    @NotBlank(message = "Quarter is required")
    @ValidQuarter(message = "Quarter must be in format Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY")
    private String quarter;
    
    @NotBlank(message = "Sex is required")
    @Size(min = 1, max = 20, message = "Sex must be between 1 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Sex can only contain letters and spaces")
    private String sex;
    
    @NotBlank(message = "Unit is required")
    @Size(min = 1, max = 10, message = "Unit must be between 1 and 10 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\%]+$", message = "Unit can only contain letters, numbers, spaces, and %")
    private String unit;
    
    @NotNull(message = "Value is required")
    @ValidValue(message = "Value must be between 0.0 and 999.99")
    @DecimalMin(value = "0.0", inclusive = true, message = "Value must be greater than or equal to 0.0")
    @DecimalMax(value = "999.99", inclusive = true, message = "Value must be less than or equal to 999.99")
    @Digits(integer = 3, fraction = 2, message = "Value can have maximum 3 digits before decimal and 2 after")
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