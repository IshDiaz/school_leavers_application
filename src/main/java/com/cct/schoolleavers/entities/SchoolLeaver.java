package com.cct.schoolleavers.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * School Leaver Entity
 * 
 * Simple entity that matches the school_leavers table exactly.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Entity
@Table(name = "school_leavers")
public class SchoolLeaver {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "statistic_code", nullable = false, length = 20)
    private String statisticCode;
    
    @Column(name = "statistic_label", nullable = false, length = 100)
    private String statisticLabel;
    
    @Column(name = "quarter", nullable = false, length = 10)
    private String quarter;
    
    @Column(name = "sex", nullable = false, length = 20)
    private String sex;
    
    @Column(name = "unit", nullable = false, length = 10)
    private String unit;
    
    @Column(name = "value", nullable = false, precision = 5, scale = 2)
    private BigDecimal value;
    
    // Default constructor
    public SchoolLeaver() {}
    
    // Constructor with all fields
    public SchoolLeaver(Long id, String statisticCode, String statisticLabel, String quarter, String sex, String unit, BigDecimal value) {
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
        return "SchoolLeaver{" +
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