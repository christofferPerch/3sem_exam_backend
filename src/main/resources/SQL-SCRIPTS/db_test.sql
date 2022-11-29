-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema startcode_test
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `startcode_test` ;

-- -----------------------------------------------------
-- Schema startcode_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `startcode_test` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `startcode_test` ;

-- -----------------------------------------------------
-- Table `startcode_test`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode_test`.`roles` ;

CREATE TABLE IF NOT EXISTS `startcode_test`.`roles` (
    `role_name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`role_name`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `startcode_test`.`cityinfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode_test`.`cityinfo` ;

CREATE TABLE IF NOT EXISTS `startcode_test`.`cityinfo` (
                                                      `zipcode` INT NOT NULL,
                                                      `city_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`zipcode`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `startcode_test`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode_test`.`address` ;

CREATE TABLE IF NOT EXISTS `startcode_test`.`address` (
                                                     `address_id` INT NOT NULL AUTO_INCREMENT,
                                                     `street_address` VARCHAR(45) NOT NULL,
    `zipcode` INT NOT NULL,
    PRIMARY KEY (`address_id`),
    INDEX `fk_address_cityinfo1_idx` (`zipcode` ASC) VISIBLE,
    CONSTRAINT `fk_address_cityinfo1`
    FOREIGN KEY (`zipcode`)
    REFERENCES `startcode_test`.`cityinfo` (`zipcode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `startcode_test`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode_test`.`user` ;

CREATE TABLE IF NOT EXISTS `startcode_test`.`user` (
    `user_name` VARCHAR(25) NOT NULL,
    `user_email` VARCHAR(255) NOT NULL,
    `user_pass` VARCHAR(255) NOT NULL,
    `address_id` INT NOT NULL,
    PRIMARY KEY (`user_name`),
    INDEX `fk_users_address1_idx` (`address_id` ASC) VISIBLE,
    CONSTRAINT `fk_users_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `startcode_test`.`address` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `startcode_test`.`user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode_test`.`user_roles` ;

CREATE TABLE IF NOT EXISTS `startcode_test`.`user_roles` (
    `role_name` VARCHAR(20) NOT NULL,
    `user_name` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`role_name`, `user_name`),
    INDEX `FK_user_roles_user_name` (`user_name` ASC) VISIBLE,
    CONSTRAINT `FK_user_roles_role_name`
    FOREIGN KEY (`role_name`)
    REFERENCES `startcode_test`.`roles` (`role_name`),
    CONSTRAINT `FK_user_roles_user_name`
    FOREIGN KEY (`user_name`)
    REFERENCES `startcode_test`.`user` (`user_name`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `startcode_test`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode_test`.`category` ;

CREATE TABLE IF NOT EXISTS `startcode_test`.`category` (
                                                      `category_id` INT NOT NULL AUTO_INCREMENT,
                                                      `category_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`category_id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `startcode_test`.`trainingsession`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode_test`.`trainingsession` ;

CREATE TABLE IF NOT EXISTS `startcode_test`.`trainingsession` (
                                                             `trainingsession_id` INT NOT NULL AUTO_INCREMENT,
                                                             `title` VARCHAR(45) NOT NULL,
    `time` VARCHAR(45) NOT NULL,
    `date` DATETIME NOT NULL,
    `full_address` VARCHAR(255) NOT NULL,
    `category_id` INT NOT NULL,
    `max_participants` INT NOT NULL,
    PRIMARY KEY (`trainingsession_id`),
    INDEX `fk_trainingsession_category1_idx` (`category_id` ASC) VISIBLE,
    CONSTRAINT `fk_trainingsession_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `startcode_test`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `startcode_test`.`user_trainingsessions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode_test`.`user_trainingsessions` ;

CREATE TABLE IF NOT EXISTS `startcode_test`.`user_trainingsessions` (
    `user_name` VARCHAR(25) NOT NULL,
    `trainingsession_id` INT NOT NULL,
    PRIMARY KEY (`user_name`, `trainingsession_id`),
    INDEX `fk_users_trainingsessions_trainingsession1_idx` (`trainingsession_id` ASC) VISIBLE,
    CONSTRAINT `fk_users_trainingsessions_users1`
    FOREIGN KEY (`user_name`)
    REFERENCES `startcode_test`.`user` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_users_trainingsessions_trainingsession1`
    FOREIGN KEY (`trainingsession_id`)
    REFERENCES `startcode_test`.`trainingsession` (`trainingsession_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
