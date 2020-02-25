-- Create DataBase
DROP DATABASE IF EXISTS `modbuslogger`;
CREATE DATABASE  `modbuslogger` DEFAULT CHARACTER SET utf8mb4;
USE `modbuslogger`;
-- MySQL dump 10.16  Distrib 10.1.37-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: mydatabase
-- ------------------------------------------------------
-- Server version	10.1.37-MariaDB-0+deb9u1

-- User data tables

--
-- Table structure for table `App_Role`
--
CREATE TABLE IF NOT EXISTS `App_Role` (
  `ROLE_ID` bigint(20) NOT NULL,
  `ROLE_NAME` varchar(30) NOT NULL,
  PRIMARY KEY (`ROLE_ID`),
  UNIQUE KEY `APP_ROLE_UK` (`ROLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `App_User`
--
CREATE TABLE IF NOT EXISTS `App_User` (
  `USER_ID` bigint(20) NOT NULL,
  `USER_NAME` varchar(36) NOT NULL,
  `ENCRYTED_PASSWORD` varchar(128) NOT NULL,
  `ENABLED` bit(1) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `APP_USER_UK` (`USER_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

  
--
-- Table structure for table `Persistent_Logins`
--
CREATE TABLE IF NOT EXISTS `Persistent_Logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `User_Role`
--
CREATE TABLE IF NOT EXISTS `User_Role` (
  `ID` bigint(20) NOT NULL,
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_ROLE_UK` (`USER_ID`,`ROLE_ID`),
  KEY `USER_ROLE_FK2` (`ROLE_ID`),
  CONSTRAINT `USER_ROLE_FK1` FOREIGN KEY (`USER_ID`) REFERENCES `App_User` (`USER_ID`),
  CONSTRAINT `USER_ROLE_FK2` FOREIGN KEY (`ROLE_ID`) REFERENCES `App_Role` (`ROLE_ID`)
) ENGINE=InnoDB;

  
-- ------------------------------------------------------
-- Device data tables
--
--
-- Table structure for table `devices`
--
CREATE TABLE IF NOT EXISTS `devices` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `address` SMALLINT(6) NOT NULL,		-- ModBus device address
  `type` SMALLINT(6) NOT NULL,		-- Device type (MLP = 150)
  `serial` SMALLINT(6) NOT NULL,		-- Device serial number
  `baudrate` INT(11) DEFAULT NULL,		-- RS485 Baudrate
  `hw_ver` SMALLINT(6) DEFAULT NULL,	-- Device Hardvare version
  `fw_ver` SMALLINT(6) DEFAULT NULL,	-- Device Firmware version
  `mfg_year` SMALLINT(6) DEFAULT NULL,	-- Devise manufacture year
  `min_voltage` SMALLINT(6) DEFAULT NULL,-- Device Min voltage value
  `max_voltage` SMALLINT(6) DEFAULT NULL,-- Device Max voltage value
  `current` SMALLINT(6) DEFAULT NULL,	-- Device Current consumption value
  `comment` VARCHAR(32) DEFAULT NULL,	-- Device comments string
  `enabled` TINYINT(4) DEFAULT NULL,	-- Enabled flag
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`serial`, `type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `mlp_data`
--
CREATE TABLE IF NOT EXISTS `mlp_data` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `devId` INT(10) unsigned NOT NULL,	-- Device ID from "device" table
  `date_time` DATETIME NOT NULL,	-- Date/time read device data
  `anglex` SMALLINT DEFAULT NULL,
  `angley` SMALLINT DEFAULT NULL,
  `anglez` SMALLINT DEFAULT NULL,
  `accel` SMALLINT unsigned DEFAULT NULL,
  `anglemx` SMALLINT DEFAULT NULL,
  `anglemy` SMALLINT DEFAULT NULL,
  `anglemz` SMALLINT DEFAULT NULL,
  `mag` SMALLINT unsigned DEFAULT NULL,
  `tempcase` SMALLINT DEFAULT NULL,
  `tempaccel` SMALLINT DEFAULT NULL,
  `state` SMALLINT unsigned DEFAULT NULL,
  `test` SMALLINT unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  FOREIGN KEY (`devId`) REFERENCES `devices`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB;



--------------------------------------

-- Add initial date value
 
insert into App_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED)
values (2, 'dbuser1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
insert into App_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED)
values (1, 'dbadmin1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
---
 
insert into App_Role (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_ADMIN');
 
insert into App_Role (ROLE_ID, ROLE_NAME)
values (2, 'ROLE_USER');
 
---
 
insert into User_Role (ID, USER_ID, ROLE_ID)
values (1, 1, 1);
 
insert into User_Role (ID, USER_ID, ROLE_ID)
values (2, 1, 2);

insert into User_Role (ID, USER_ID, ROLE_ID)
values (3, 2, 2);

---

INSERT INTO `devices` 
	(`address`, `type`, `serial`, `baudrate`, `hw_ver`, `fw_ver`, `mfg_year`, `min_voltage`, `max_voltage`, `current`, `comment`, `enabled`) 
	VALUES ('11', '150', '5', '9600', '10', '10', '2019', '5', '24', '30', 'comment 1', true);
    
INSERT INTO `devices` 
	(`address`, `type`, `serial`, `baudrate`, `hw_ver`, `fw_ver`, `mfg_year`, `min_voltage`, `max_voltage`, `current`, `comment`, `enabled`) 
	VALUES ('12', '150', '6', '9600', '10', '10', '2019', '5', '24', '30', 'comment 2', true);

---

INSERT INTO `mlp_data` 
	(`devId`, `anglex`, `angley`, `anglez`, `accel`, `anglemx`, `anglemy`, `anglemz`, `mag`, `tempcase`, `state`, `test`, `date_time`) 
	VALUES ('1', '15000', '15000', '55', '9600', '10', '10', '19', '5', '24', '30', 1, current_time());

