create database test;
use test;

CREATE TABLE CUSTOMER  (
  id BIGINT  NOT NULL PRIMARY KEY ,
  firstName VARCHAR(11) NOT NULL ,
  middleInitial VARCHAR(1),
  lastName VARCHAR(10) NOT NULL,
  address VARCHAR(45) NOT NULL,
  city VARCHAR(16) NOT NULL,
  state CHAR(2) NOT NULL,
  zipCode CHAR(5)
);

-- CREATE TABLE ACCOUNTEXECUTIVE (
--   id BIGINT NOT NULL PRIMARY KEY ,
--   firstName VARCHAR(45) NOT NULL,
--   lastName VARCHAR(45) NOT NULL
-- )