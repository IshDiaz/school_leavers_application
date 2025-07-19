package com.cct.schoolleavers.util;

import com.cct.schoolleavers.entities.SchoolLeaver;
import com.cct.schoolleavers.repositories.SchoolLeaverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Import Utility
 * 
 * This utility class provides methods to import school leavers data
 * from CSV files or other formats into the database.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Component
public class DataImportUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(DataImportUtil.class);
    
    @Autowired
    private SchoolLeaverRepository schoolLeaverRepository;
    
    /**
     * Import data from CSV file
     * 
     * @param filePath the path to the CSV file
     * @param hasHeader true if the CSV has a header row
     * @return number of records imported
     */
    public int importFromCsv(String filePath, boolean hasHeader) {
        int importedCount = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                // Skip header if present
                if (hasHeader && isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                // Parse and save the record
                SchoolLeaver schoolLeaver = parseCsvLine(line);
                if (schoolLeaver != null) {
                    schoolLeaverRepository.save(schoolLeaver);
                    importedCount++;
                }
                
                isFirstLine = false;
            }
            
            logger.info("Successfully imported {} records from CSV file: {}", importedCount, filePath);
            
        } catch (IOException e) {
            logger.error("Error importing data from CSV file: {}", e.getMessage(), e);
        }
        
        return importedCount;
    }
    
    /**
     * Parse a CSV line into a SchoolLeaver entity
     * 
     * @param line the CSV line to parse
     * @return SchoolLeaver entity or null if parsing fails
     */
    private SchoolLeaver parseCsvLine(String line) {
        try {
            // Split the line by comma, handling quoted values
            String[] fields = parseCsvFields(line);
            
            if (fields.length < 6) {
                logger.warn("Invalid CSV line (insufficient fields): {}", line);
                return null;
            }
            
            // Create SchoolLeaver entity
            SchoolLeaver schoolLeaver = new SchoolLeaver();
            schoolLeaver.setStatisticCode(fields[0].trim());
            schoolLeaver.setStatisticLabel(fields[1].trim());
            schoolLeaver.setQuarter(fields[2].trim());
            schoolLeaver.setSex(fields[3].trim());
            schoolLeaver.setUnit(fields[4].trim());
            
            // Parse value
            try {
                BigDecimal value = new BigDecimal(fields[5].trim());
                schoolLeaver.setValue(value);
            } catch (NumberFormatException e) {
                logger.warn("Invalid value format in CSV line: {}", line);
                return null;
            }
            
            return schoolLeaver;
            
        } catch (Exception e) {
            logger.warn("Error parsing CSV line: {}", line, e);
            return null;
        }
    }
    
    /**
     * Parse CSV fields, handling quoted values
     * 
     * @param line the CSV line
     * @return array of fields
     */
    private String[] parseCsvFields(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        
        // Add the last field
        fields.add(currentField.toString());
        
        return fields.toArray(new String[0]);
    }
    
    /**
     * Import sample data for testing
     * 
     * @return number of records imported
     */
    public int importSampleData() {
        List<SchoolLeaver> sampleData = createSampleData();
        int importedCount = 0;
        
        for (SchoolLeaver schoolLeaver : sampleData) {
            try {
                schoolLeaverRepository.save(schoolLeaver);
                importedCount++;
            } catch (Exception e) {
                logger.warn("Error saving sample record: {}", e.getMessage());
            }
        }
        
        logger.info("Successfully imported {} sample records", importedCount);
        return importedCount;
    }
    
    /**
     * Create sample data for testing
     * 
     * @return List of sample SchoolLeaver entities
     */
    private List<SchoolLeaver> createSampleData() {
        List<SchoolLeaver> sampleData = new ArrayList<>();
        
        // Sample data entries
        sampleData.add(new SchoolLeaver("SL001", "Total School Leavers", "Q12023", "Male", "Count", new BigDecimal("1250.50")));
        sampleData.add(new SchoolLeaver("SL001", "Total School Leavers", "Q12023", "Female", "Count", new BigDecimal("1180.25")));
        sampleData.add(new SchoolLeaver("SL002", "Employment Rate", "Q12023", "Male", "Percent", new BigDecimal("65.75")));
        sampleData.add(new SchoolLeaver("SL002", "Employment Rate", "Q12023", "Female", "Percent", new BigDecimal("62.30")));
        sampleData.add(new SchoolLeaver("SL003", "Further Education", "Q12023", "Male", "Count", new BigDecimal("450.00")));
        sampleData.add(new SchoolLeaver("SL003", "Further Education", "Q12023", "Female", "Count", new BigDecimal("520.75")));
        sampleData.add(new SchoolLeaver("SL001", "Total School Leavers", "Q22023", "Male", "Count", new BigDecimal("1320.00")));
        sampleData.add(new SchoolLeaver("SL001", "Total School Leavers", "Q22023", "Female", "Count", new BigDecimal("1250.50")));
        sampleData.add(new SchoolLeaver("SL002", "Employment Rate", "Q22023", "Male", "Percent", new BigDecimal("68.25")));
        sampleData.add(new SchoolLeaver("SL002", "Employment Rate", "Q22023", "Female", "Percent", new BigDecimal("64.80")));
        
        return sampleData;
    }
    
    /**
     * Clear all school leavers data
     * 
     * @return number of records deleted
     */
    public long clearAllData() {
        long count = schoolLeaverRepository.count();
        schoolLeaverRepository.deleteAll();
        logger.info("Cleared {} school leavers records", count);
        return count;
    }
    
    /**
     * Get data statistics
     * 
     * @return statistics string
     */
    public String getDataStatistics() {
        long totalRecords = schoolLeaverRepository.count();
        List<String> distinctCodes = schoolLeaverRepository.findDistinctStatisticCodes();
        List<String> distinctQuarters = schoolLeaverRepository.findDistinctQuarters();
        List<String> distinctSexes = schoolLeaverRepository.findDistinctSexes();
        
        StringBuilder stats = new StringBuilder();
        stats.append("Data Statistics:\n");
        stats.append("- Total Records: ").append(totalRecords).append("\n");
        stats.append("- Distinct Statistic Codes: ").append(distinctCodes.size()).append("\n");
        stats.append("- Distinct Quarters: ").append(distinctQuarters.size()).append("\n");
        stats.append("- Distinct Sexes: ").append(distinctSexes.size()).append("\n");
        
        if (!distinctCodes.isEmpty()) {
            stats.append("- Statistic Codes: ").append(String.join(", ", distinctCodes)).append("\n");
        }
        
        if (!distinctQuarters.isEmpty()) {
            stats.append("- Quarters: ").append(String.join(", ", distinctQuarters)).append("\n");
        }
        
        if (!distinctSexes.isEmpty()) {
            stats.append("- Sexes: ").append(String.join(", ", distinctSexes)).append("\n");
        }
        
        return stats.toString();
    }
} 