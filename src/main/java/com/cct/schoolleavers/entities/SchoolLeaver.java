package com.cct.schoolleavers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * School Leaver Entity
 * 
 * This entity represents the school_leavers table in the database.
 * It handles school leavers statistics data with various metrics.
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
    
    @NotBlank(message = "Statistic code is required")
    @Size(max = 20, message = "Statistic code must not exceed 20 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Statistic code must contain only uppercase letters and numbers")
    @Column(name = "statistic_code", nullable = false, length = 20)
    private String statisticCode;
    
    @NotBlank(message = "Statistic label is required")
    @Size(max = 100, message = "Statistic label must not exceed 100 characters")
    @Column(name = "statistic_label", nullable = false, length = 100)
    private String statisticLabel;
    
    @NotBlank(message = "Quarter is required")
    @Size(max = 10, message = "Quarter must not exceed 10 characters")
    @Pattern(regexp = "^[Q][1-4]\\d{4}$", message = "Quarter must be in format Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY")
    @Column(name = "quarter", nullable = false, length = 10)
    private String quarter;
    
    @NotBlank(message = "Sex is required")
    @Size(max = 20, message = "Sex must not exceed 20 characters")
    @Column(name = "sex", nullable = false, length = 20)
    private String sex;
    
    @NotBlank(message = "Unit is required")
    @Size(max = 10, message = "Unit must not exceed 10 characters")
    @Column(name = "unit", nullable = false, length = 10)
    private String unit;
    
    @NotNull(message = "Value is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Value must be greater than or equal to 0")
    @DecimalMax(value = "999.99", inclusive = true, message = "Value must be less than or equal to 999.99")
    @Digits(integer = 3, fraction = 2, message = "Value must have up to 3 digits before decimal and 2 after")
    @Column(name = "value", nullable = false, precision = 5, scale = 2)
    private BigDecimal value;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Default constructor
    public SchoolLeaver() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Constructor with required fields
    public SchoolLeaver(String statisticCode, String statisticLabel, String quarter, String sex, String unit, BigDecimal value) {
        this();
        this.statisticCode = statisticCode;
        this.statisticLabel = statisticLabel;
        this.quarter = quarter;
        this.sex = sex;
        this.unit = unit;
        this.value = value;
    }
    
    // Constructor with all fields
    public SchoolLeaver(Long id, String statisticCode, String statisticLabel, String quarter, String sex, String unit, BigDecimal value) {
        this();
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
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getStatisticLabel() {
        return statisticLabel;
    }
    
    public void setStatisticLabel(String statisticLabel) {
        this.statisticLabel = statisticLabel;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getQuarter() {
        return quarter;
    }
    
    public void setQuarter(String quarter) {
        this.quarter = quarter;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
        this.updatedAt = LocalDateTime.now();
    }
    
    public BigDecimal getValue() {
        return value;
    }
    
    public void setValue(BigDecimal value) {
        this.value = value;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Utility methods
    public String getYear() {
        if (quarter != null && quarter.length() >= 5) {
            return quarter.substring(1, 5);
        }
        return null;
    }
    
    public String getQuarterNumber() {
        if (quarter != null && quarter.length() >= 2) {
            return quarter.substring(0, 2);
        }
        return null;
    }
    
    public String getFormattedValue() {
        if (value != null) {
            return value.toString();
        }
        return "0.00";
    }
    
    // Pre-persist method to set timestamps
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Pre-update method to set updated timestamp
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
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
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        SchoolLeaver that = (SchoolLeaver) o;
        
        if (!id.equals(that.id)) return false;
        if (!statisticCode.equals(that.statisticCode)) return false;
        if (!quarter.equals(that.quarter)) return false;
        if (!sex.equals(that.sex)) return false;
        return unit.equals(that.unit);
    }
    
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + statisticCode.hashCode();
        result = 31 * result + quarter.hashCode();
        result = 31 * result + sex.hashCode();
        result = 31 * result + unit.hashCode();
        return result;
    }
} 