CREATE TABLE `test`.`student` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `groupId` INT NULL,
  PRIMARY KEY (`id`));

insert into student values (1, 'a', 1);
insert into student values (2, 'b', 2);
insert into student values (3, 'c', 1);
insert into student values (4, 'd', 4);