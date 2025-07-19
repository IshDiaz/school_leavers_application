-- ===========================================
-- SCHOOL LEAVERS SYSTEM - DATABASE SETUP
-- ===========================================
-- This script sets up the complete database for the School Leavers CRUD System
-- It creates both tables and inserts the required data

-- ===========================================
-- CREATE TABLES
-- ===========================================

-- Create users table
CREATE TABLE IF NOT EXISTS `users` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(100) NOT NULL,
    `enabled` tinyint(1) NOT NULL DEFAULT 1,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `last_login` datetime NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create school_leavers table
CREATE TABLE IF NOT EXISTS `school_leavers` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `statistic_code` varchar(20) NOT NULL,
    `statistic_label` varchar(100) NOT NULL,
    `quarter` varchar(10) NOT NULL,
    `sex` varchar(20) NOT NULL,
    `unit` varchar(10) NOT NULL,
    `value` decimal(5,2) NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===========================================
-- INSERT REQUIRED USER DATA
-- ===========================================

-- Insert the required default user (CCT1234/54321)
INSERT INTO `users` (`username`, `password`, `enabled`, `created_at`, `updated_at`) 
VALUES ('CCT1234', '54321', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE 
    `password` = VALUES(`password`),
    `enabled` = VALUES(`enabled`),
    `updated_at` = NOW();

-- Insert additional test users
INSERT INTO `users` (`username`, `password`, `enabled`, `created_at`, `updated_at`) 
VALUES ('testuser1', 'password123', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE 
    `password` = VALUES(`password`),
    `enabled` = VALUES(`enabled`),
    `updated_at` = NOW();

INSERT INTO `users` (`username`, `password`, `enabled`, `created_at`, `updated_at`) 
VALUES ('testuser2', 'password456', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE 
    `password` = VALUES(`password`),
    `enabled` = VALUES(`enabled`),
    `updated_at` = NOW();

-- ===========================================
-- SCHOOL LEAVERS DATA
-- ===========================================
-- Note: School leavers data should be imported from your CSV dataset
-- This script only creates the table structure
-- Use the DataImportUtil class or your preferred method to import the actual data

-- ===========================================
-- VERIFICATION QUERIES
-- ===========================================

-- Verify users were created
SELECT '=== USER VERIFICATION ===' as section;
SELECT 
    'Default user created successfully' as message,
    id,
    username,
    enabled,
    created_at
FROM `users` 
WHERE username = 'CCT1234';

-- Display all users
SELECT 
    'All users in database:' as message,
    id,
    username,
    enabled,
    created_at
FROM `users`
ORDER BY id;

-- Verify school leavers data
SELECT '=== SCHOOL LEAVERS DATA VERIFICATION ===' as section;
SELECT 
    'Total school leavers records:' as message,
    COUNT(*) as total_records
FROM `school_leavers`;

-- Note: Display actual data from your imported dataset
SELECT 
    'Your imported school leavers data:' as message,
    statistic_code,
    statistic_label,
    quarter,
    sex,
    unit,
    value
FROM `school_leavers`
ORDER BY statistic_code, quarter, sex
LIMIT 10;

-- Display statistics summary
SELECT '=== STATISTICS SUMMARY ===' as section;
SELECT 
    'Total Users:' as metric,
    COUNT(*) as value
FROM `users`
UNION ALL
SELECT 
    'Total School Leavers Records:' as metric,
    COUNT(*) as value
FROM `school_leavers`
UNION ALL
SELECT 
    'Distinct Statistic Codes:' as metric,
    COUNT(DISTINCT statistic_code) as value
FROM `school_leavers`
UNION ALL
SELECT 
    'Distinct Quarters:' as metric,
    COUNT(DISTINCT quarter) as value
FROM `school_leavers`
UNION ALL
SELECT 
    'Average Value:' as metric,
    ROUND(AVG(value), 2) as value
FROM `school_leavers`; 