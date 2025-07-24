package com.cct.schoolleavers.dto;

import java.math.BigDecimal;

/**
 * School Leaver Data Transfer Object
 * 
 * Simple DTO for data transfer.
 * 
 * @author CCT Student
 * @version 1.0
 */
public class SchoolLeaverDto {
    
    private Long id;
    private String statisticCode;
    private String statisticLabel;
    private String quarter;
    private String sex;
    private String unit;
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