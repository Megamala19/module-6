-- Run this against a MySQL schema named `ormlearn` (or update
-- application.properties to point at your own schema name).
-- CREATE DATABASE IF NOT EXISTS ormlearn;
-- USE ormlearn;

-- ============================================================
-- Hands-on 2, 4, 5: Employee / Department / Skill
-- ============================================================
CREATE TABLE IF NOT EXISTS department (
    dp_id INT AUTO_INCREMENT PRIMARY KEY,
    dp_name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS employee (
    em_id INT AUTO_INCREMENT PRIMARY KEY,
    em_name VARCHAR(100),
    em_date_of_birth DATE,
    em_permanent BIT,
    em_salary DOUBLE,
    em_dp_id INT,
    FOREIGN KEY (em_dp_id) REFERENCES department(dp_id)
);

CREATE TABLE IF NOT EXISTS skill (
    sk_id INT AUTO_INCREMENT PRIMARY KEY,
    sk_name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS employee_skill (
    es_em_id INT,
    es_sk_id INT,
    PRIMARY KEY (es_em_id, es_sk_id),
    FOREIGN KEY (es_em_id) REFERENCES employee(em_id),
    FOREIGN KEY (es_sk_id) REFERENCES skill(sk_id)
);

INSERT INTO department (dp_name) VALUES ('Engineering'), ('HR');

INSERT INTO employee (em_name, em_date_of_birth, em_permanent, em_salary, em_dp_id) VALUES
 ('Alice', '1990-04-12', 1, 75000, 1),
 ('Bob',   '1988-11-02', 1, 68000, 1),
 ('Carol', '1995-06-23', 0, 50000, 2);

INSERT INTO skill (sk_name) VALUES ('Java'), ('SQL'), ('Spring');

INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES
 (1, 1), (1, 3), (2, 2);

-- ============================================================
-- Hands-on 3: Quiz attempt tracking
-- ============================================================
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS question (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS options (
    id INT AUTO_INCREMENT PRIMARY KEY,
    option_text VARCHAR(200),
    score DOUBLE,
    question_id INT,
    FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE IF NOT EXISTS attempt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    attempted_date DATETIME,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS attempt_question (
    id INT AUTO_INCREMENT PRIMARY KEY,
    attempt_id INT,
    question_id INT,
    FOREIGN KEY (attempt_id) REFERENCES attempt(id),
    FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE IF NOT EXISTS attempt_option (
    id INT AUTO_INCREMENT PRIMARY KEY,
    attempt_question_id INT,
    option_id INT,
    FOREIGN KEY (attempt_question_id) REFERENCES attempt_question(id),
    FOREIGN KEY (option_id) REFERENCES options(id)
);

INSERT INTO user (username) VALUES ('jdoe');

INSERT INTO question (question_text) VALUES
 ('What is the extension of the hyper text markup language file?'),
 ('What is the maximum level of heading tag can be used in a HTML page?');

INSERT INTO options (option_text, score, question_id) VALUES
 ('.xhtm', 0.0, 1), ('.ht', 0.0, 1), ('.html', 1.0, 1), ('.htmx', 0.0, 1),
 ('5', 0.0, 2), ('3', 0.0, 2), ('4', 0.0, 2), ('6', 1.0, 2);

INSERT INTO attempt (user_id, attempted_date) VALUES (1, NOW());

INSERT INTO attempt_question (attempt_id, question_id) VALUES (1, 1), (1, 2);

-- user selected '.html' (correct) for question 1, and '5' (incorrect) for question 2
INSERT INTO attempt_option (attempt_question_id, option_id) VALUES (1, 3), (2, 5);
