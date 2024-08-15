CREATE TABLE `test`.`employee` (
  `empId` INT NOT NULL,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `age` INT NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`empId`));

  INSERT INTO `test`.`employee` (`empId`, `firstName`, `lastName`, `age`, `email`) VALUES ('1', 'John', 'Doe', '22', 'john.doe@gmail.com');
  INSERT INTO `test`.`employee` (`empId`, `firstName`, `lastName`, `age`, `email`) VALUES ('2', 'Jane', 'Doe', '33', 'jane.doe@gmail.com');
  INSERT INTO `test`.`employee` (`empId`, `firstName`, `lastName`, `age`, `email`) VALUES ('3', 'Mike', 'Doe', '44', 'mike.doe@gmail.com');
  INSERT INTO `test`.`employee` (`empId`, `firstName`, `lastName`, `age`, `email`) VALUES ('4', 'Harshita', 'Dekate', '55', 'harshita.dekate@gmail.com');