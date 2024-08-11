CREATE TABLE `test`.`person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

 CREATE TABLE `test`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `personId` INT NULL,
  `street` VARCHAR(200) NULL,
  PRIMARY KEY (`id`));
 
 
INSERT INTO `test`.`person` (`id`, `name`) VALUES ('1', 'Suchita');
INSERT INTO `test`.`person` (`id`) VALUES ('2');

INSERT INTO `test`.`address` (`id`, `personId`, `street`) VALUES ('1', '1', 'Achalpur City Street');
INSERT INTO `test`.`address` (`id`, `personId`, `street`) VALUES ('2', '2', 'Morshi City Street');
 