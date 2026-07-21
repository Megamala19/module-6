-- Run this against the ormlearn schema before starting the app with the
-- "mysql" profile (ddl-auto=validate expects the table to already exist).
--
--   mysql -u root -p
--   mysql> create schema ormlearn;
--   mysql> use ormlearn;
--   mysql> source schema-mysql.sql;
--   mysql> source data.sql;

create table country (
    co_code varchar(2) primary key,
    co_name varchar(50)
);

-- Hands on 2 (doc 2): stock table
CREATE TABLE IF NOT EXISTS `stock` (
  `st_id` INT NOT NULL AUTO_INCREMENT,
  `st_code` varchar(10),
  `st_date` date,
  `st_open` numeric(10,2),
  `st_close` numeric(10,2),
  `st_volume` numeric,
  PRIMARY KEY (`st_id`)
);

-- Hands on 3-6 (doc 2): payroll tables - department, skill, employee, employee_skill
CREATE TABLE IF NOT EXISTS `department` (
  `dp_id` INT NOT NULL AUTO_INCREMENT,
  `dp_name` varchar(50),
  PRIMARY KEY (`dp_id`)
);

CREATE TABLE IF NOT EXISTS `skill` (
  `sk_id` INT NOT NULL AUTO_INCREMENT,
  `sk_name` varchar(50),
  PRIMARY KEY (`sk_id`)
);

CREATE TABLE IF NOT EXISTS `employee` (
  `em_id` INT NOT NULL AUTO_INCREMENT,
  `em_name` varchar(100),
  `em_salary` decimal(10,2),
  `em_permanent` boolean,
  `em_date_of_birth` date,
  `em_dp_id` INT,
  PRIMARY KEY (`em_id`),
  FOREIGN KEY (`em_dp_id`) REFERENCES `department` (`dp_id`)
);

CREATE TABLE IF NOT EXISTS `employee_skill` (
  `es_em_id` INT NOT NULL,
  `es_sk_id` INT NOT NULL,
  PRIMARY KEY (`es_em_id`, `es_sk_id`),
  FOREIGN KEY (`es_em_id`) REFERENCES `employee` (`em_id`),
  FOREIGN KEY (`es_sk_id`) REFERENCES `skill` (`sk_id`)
);
