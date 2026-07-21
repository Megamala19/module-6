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
