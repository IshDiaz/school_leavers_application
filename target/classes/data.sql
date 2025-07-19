-- ===========================================
-- SCHOOL LEAVERS SYSTEM - DATA INITIALIZATION
-- ===========================================

-- Insert default user for authentication
-- Username: CCT1234, Password: 54321

INSERT INTO users (username, password, enabled, created_at, updated_at) 
VALUES ('CCT1234', '54321', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE 
    password = VALUES(password),
    enabled = VALUES(enabled),
    updated_at = NOW();

-- Insert additional test users (optional)
INSERT INTO users (username, password, enabled, created_at, updated_at) 
VALUES ('testuser1', 'password123', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE 
    password = VALUES(password),
    enabled = VALUES(enabled),
    updated_at = NOW();

INSERT INTO users (username, password, enabled, created_at, updated_at) 
VALUES ('testuser2', 'password456', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE 
    password = VALUES(password),
    enabled = VALUES(enabled),
    updated_at = NOW();

-- Display inserted users
SELECT 'Default user created successfully' as message, username, enabled, created_at 
FROM users 
WHERE username IN ('CCT1234', 'testuser1', 'testuser2'); 