-- ===========================================
-- CREATE SCHOOL LEAVERS TABLE
-- ===========================================
-- Run this script in your MySQL database to create the school_leavers table

-- Create school_leavers table without timestamp columns
CREATE TABLE IF NOT EXISTS `school_leavers` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `statistic_code` varchar(20) NOT NULL,
    `statistic_label` varchar(100) NOT NULL,
    `quarter` varchar(10) NOT NULL,
    `sex` varchar(20) NOT NULL,
    `unit` varchar(10) NOT NULL,
    `value` decimal(5,2) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert sample data
INSERT INTO `school_leavers` (`statistic_code`, `statistic_label`, `quarter`, `sex`, `unit`, `value`) VALUES
('SL001', 'School Leavers - Total', '2023-Q1', 'All', 'Number', 1250.50),
('SL002', 'School Leavers - Male', '2023-Q1', 'Male', 'Number', 650.25),
('SL003', 'School Leavers - Female', '2023-Q1', 'Female', 'Number', 600.25),
('SL001', 'School Leavers - Total', '2023-Q2', 'All', 'Number', 1300.75),
('SL002', 'School Leavers - Male', '2023-Q2', 'Male', 'Number', 675.50);

-- Verify the table was created
SELECT 'School leavers table created successfully' as message;
SELECT COUNT(*) as total_records FROM `school_leavers`; 