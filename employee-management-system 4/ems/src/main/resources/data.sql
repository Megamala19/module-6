-- Runs automatically on startup against the H2 in-memory database
-- (ddl-auto=update creates the tables first, then this seeds them).

INSERT INTO department (id, name, created_date, last_modified_date, created_by, last_modified_by) VALUES
 (1, 'Engineering', NOW(), NOW(), 'system', 'system'),
 (2, 'Human Resources', NOW(), NOW(), 'system', 'system'),
 (3, 'Sales', NOW(), NOW(), 'system', 'system');

INSERT INTO employee (id, name, email, department_id, created_date, last_modified_date, created_by, last_modified_by) VALUES
 (1, 'Alice Johnson', 'alice.johnson@example.com', 1, NOW(), NOW(), 'system', 'system'),
 (2, 'Bob Smith', 'bob.smith@example.com', 1, NOW(), NOW(), 'system', 'system'),
 (3, 'Carol Lee', 'carol.lee@example.com', 2, NOW(), NOW(), 'system', 'system'),
 (4, 'David Kim', 'david.kim@example.com', 3, NOW(), NOW(), 'system', 'system');

ALTER SEQUENCE department_seq RESTART WITH 4;
ALTER SEQUENCE employee_seq RESTART WITH 5;
