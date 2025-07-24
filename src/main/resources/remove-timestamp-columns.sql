-- Remove timestamp columns from school_leavers table
-- Run this script after manually removing the columns from your database

-- Alternative: If you want to recreate the table without timestamp columns
-- DROP TABLE IF EXISTS school_leavers;
-- CREATE TABLE school_leavers (
--     id bigint(20) NOT NULL AUTO_INCREMENT,
--     statistic_code varchar(20) NOT NULL,
--     statistic_label varchar(100) NOT NULL,
--     quarter varchar(10) NOT NULL,
--     sex varchar(20) NOT NULL,
--     unit varchar(10) NOT NULL,
--     value decimal(5,2) NOT NULL,
--     PRIMARY KEY (id)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Or if you prefer to just remove the columns:
-- ALTER TABLE school_leavers DROP COLUMN created_at;
-- ALTER TABLE school_leavers DROP COLUMN updated_at; 