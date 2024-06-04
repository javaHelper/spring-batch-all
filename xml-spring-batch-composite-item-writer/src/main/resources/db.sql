CREATE TABLE `test`.`customer` (
  `id` INT NOT NULL,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `birthDate` DATETIME NULL,
  PRIMARY KEY (`id`));
  
INSERT INTO test.customer (id, firstName, lastName, birthdate) VALUES(1, 'John', 'Doe', 'john.doe@gmail.com');
INSERT INTO test.customer (id, firstName, lastName, birthdate) VALUES(2, 'Jane', 'Doe', 'jane.doe@gmail.com');