-- ===========================================
-- INSERT REQUIRED USER FOR ASSESSMENT
-- ===========================================
-- This script inserts ONLY the required login credentials for the assessment
-- Username: CCT1234
-- Password: 54321

-- Insert the required default user (CCT1234/54321)
INSERT INTO `users` (`username`, `password`, `enabled`, `created_at`, `updated_at`) 
VALUES ('CCT1234', '54321', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE 
    `password` = VALUES(`password`),
    `enabled` = VALUES(`enabled`),
    `updated_at` = NOW();

-- Verify the user was created
SELECT 
    'Required user created successfully' as message,
    id,
    username,
    enabled,
    created_at
FROM `users` 
WHERE username = 'CCT1234'; 