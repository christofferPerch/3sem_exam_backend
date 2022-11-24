-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema 3sem_exam
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `3sem_exam` ;

-- -----------------------------------------------------
-- Schema 3sem_exam
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `3sem_exam` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `3sem_exam` ;

-- -----------------------------------------------------
-- Table `3sem_exam`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `3sem_exam`.`roles` ;

CREATE TABLE IF NOT EXISTS `3sem_exam`.`roles` (
    `role_name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`role_name`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `3sem_exam`.`cityinfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `3sem_exam`.`cityinfo` ;

CREATE TABLE IF NOT EXISTS `3sem_exam`.`cityinfo` (
                                                      `zipcode` INT NOT NULL,
                                                      `city_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`zipcode`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `3sem_exam`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `3sem_exam`.`address` ;

CREATE TABLE IF NOT EXISTS `3sem_exam`.`address` (
                                                     `address_id` INT NOT NULL AUTO_INCREMENT,
                                                     `street_address` VARCHAR(45) NOT NULL,
    `zipcode` INT NOT NULL,
    PRIMARY KEY (`address_id`),
    INDEX `fk_address_cityinfo1_idx` (`zipcode` ASC) VISIBLE,
    CONSTRAINT `fk_address_cityinfo1`
    FOREIGN KEY (`zipcode`)
    REFERENCES `3sem_exam`.`cityinfo` (`zipcode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `3sem_exam`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `3sem_exam`.`user` ;

CREATE TABLE IF NOT EXISTS `3sem_exam`.`user` (
    `user_name` VARCHAR(25) NOT NULL,
    `user_email` VARCHAR(255) NOT NULL,
    `user_pass` VARCHAR(255) NOT NULL,
    `address_id` INT NOT NULL,
    PRIMARY KEY (`user_name`),
    INDEX `fk_users_address1_idx` (`address_id` ASC) VISIBLE,
    CONSTRAINT `fk_users_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `3sem_exam`.`address` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `3sem_exam`.`user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `3sem_exam`.`user_roles` ;

CREATE TABLE IF NOT EXISTS `3sem_exam`.`user_roles` (
    `role_name` VARCHAR(20) NOT NULL,
    `user_name` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`role_name`, `user_name`),
    INDEX `FK_user_roles_user_name` (`user_name` ASC) VISIBLE,
    CONSTRAINT `FK_user_roles_role_name`
    FOREIGN KEY (`role_name`)
    REFERENCES `3sem_exam`.`roles` (`role_name`),
    CONSTRAINT `FK_user_roles_user_name`
    FOREIGN KEY (`user_name`)
    REFERENCES `3sem_exam`.`user` (`user_name`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `3sem_exam`.`location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `3sem_exam`.`location` ;

CREATE TABLE IF NOT EXISTS `3sem_exam`.`location` (
                                                      `location_id` INT NOT NULL AUTO_INCREMENT,
                                                      `location_coordinates` VARCHAR(255) NOT NULL,
    `location_city` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`location_id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `3sem_exam`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `3sem_exam`.`category` ;

CREATE TABLE IF NOT EXISTS `3sem_exam`.`category` (
                                                      `category_id` INT NOT NULL AUTO_INCREMENT,
                                                      `category_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`category_id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `3sem_exam`.`trainingsession`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `3sem_exam`.`trainingsession` ;

CREATE TABLE IF NOT EXISTS `3sem_exam`.`trainingsession` (
                                                             `trainingsession_id` INT NOT NULL AUTO_INCREMENT,
                                                             `title` VARCHAR(45) NOT NULL,
    `time` VARCHAR(45) NOT NULL,
    `date` DATETIME NOT NULL,
    `location_id` INT NOT NULL,
    `category_id` INT NOT NULL,
    `max_participants` INT NOT NULL,
    PRIMARY KEY (`trainingsession_id`),
    INDEX `fk_trainingsession_location1_idx` (`location_id` ASC) VISIBLE,
    INDEX `fk_trainingsession_category1_idx` (`category_id` ASC) VISIBLE,
    CONSTRAINT `fk_trainingsession_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `3sem_exam`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_trainingsession_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `3sem_exam`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `3sem_exam`.`user_trainingsessions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `3sem_exam`.`user_trainingsessions` ;

CREATE TABLE IF NOT EXISTS `3sem_exam`.`user_trainingsessions` (
    `user_name` VARCHAR(25) NOT NULL,
    `trainingsession_id` INT NOT NULL,
    PRIMARY KEY (`user_name`, `trainingsession_id`),
    INDEX `fk_users_trainingsessions_trainingsession1_idx` (`trainingsession_id` ASC) VISIBLE,
    CONSTRAINT `fk_users_trainingsessions_users1`
    FOREIGN KEY (`user_name`)
    REFERENCES `3sem_exam`.`user` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_users_trainingsessions_trainingsession1`
    FOREIGN KEY (`trainingsession_id`)
    REFERENCES `3sem_exam`.`trainingsession` (`trainingsession_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
