
-- Create DataBase

CREATE DATABASE  IF NOT EXISTS `mydatabase` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `mydatabase`;
-- MySQL dump 10.16  Distrib 10.1.37-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: mydatabase
-- ------------------------------------------------------
-- Server version	10.1.37-MariaDB-0+deb9u1


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `devices`
--
CREATE TABLE IF NOT EXISTS `devices` (
  `devId` int(11) NOT NULL AUTO_INCREMENT,
  `address` smallint(6) NOT NULL,
  `type` smallint(6) NOT NULL,
  `serial` smallint(6) NOT NULL,
  `baudrate` int(11) DEFAULT NULL,
  `hw_ver` smallint(6) DEFAULT NULL,
  `fw_ver` smallint(6) DEFAULT NULL,
  `mfg_year` smallint(6) DEFAULT NULL,
  `min_voltage` smallint(6) DEFAULT NULL,
  `max_voltage` smallint(6) DEFAULT NULL,
  `current` smallint(6) DEFAULT NULL,
  `comment` varchar(32) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`devId`),
  UNIQUE KEY `id_UNIQUE` (`devId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mlp_data`
--
CREATE TABLE IF NOT EXISTS `mlp_data` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dev_id` int(10) unsigned NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `anglex` smallint(5) DEFAULT NULL,
  `angley` smallint(5) DEFAULT NULL,
  `anglez` smallint(5) DEFAULT NULL,
  `accel` smallint(5) unsigned DEFAULT NULL,
  `anglemx` smallint(5) DEFAULT NULL,
  `anglemy` smallint(5) DEFAULT NULL,
  `anglemz` smallint(5) DEFAULT NULL,
  `mag` smallint(5) unsigned DEFAULT NULL,
  `tempcase` smallint(5) DEFAULT NULL,
  `tempaccel` smallint(5) DEFAULT NULL,
  `state` smallint(5) unsigned DEFAULT NULL,
  `test` smallint(5) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2460 DEFAULT CHARSET=utf8mb4;

-- Create table
create table App_User
(
  USER_ID           BIGINT not null,
  USER_NAME         VARCHAR(36) not null,
  ENCRYTED_PASSWORD VARCHAR(128) not null,
  ENABLED           BIT not null 
) ;
--  
alter table App_User
  add constraint APP_USER_PK primary key (USER_ID);
 
alter table App_User
  add constraint APP_USER_UK unique (USER_NAME);
 
 
-- Create table
create table App_Role
(
  ROLE_ID   BIGINT not null,
  ROLE_NAME VARCHAR(30) not null
) ;
--  
alter table App_Role
  add constraint APP_ROLE_PK primary key (ROLE_ID);
 
alter table App_Role
  add constraint APP_ROLE_UK unique (ROLE_NAME);
 
 
-- Create table
create table User_Role
(
  ID      BIGINT not null,
  USER_ID BIGINT not null,
  ROLE_ID BIGINT not null
);
--  
alter table User_Role
  add constraint USER_ROLE_PK primary key (ID);
 
alter table User_Role
  add constraint USER_ROLE_UK unique (USER_ID, ROLE_ID);
 
alter table User_Role
  add constraint USER_ROLE_FK1 foreign key (USER_ID)
  references App_User (USER_ID);
 
alter table User_Role
  add constraint USER_ROLE_FK2 foreign key (ROLE_ID)
  references App_Role (ROLE_ID);
 
 
-- Used by Spring Remember Me API.  
CREATE TABLE Persistent_Logins (
 
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
     
);
 

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


INSERT INTO `mydatabase`.`devices` 
	(`id`, `address`, `type`, `serial`, `baudrate`, `hw_ver`, `fw_ver`, `mfg_year`, `min_voltage`, `max_voltage`, `current`) 
	VALUES ('3', '11', '150', '5', '9600', '10', '10', '2019', '5', '24', '30');


-- Dump completed on 2019-08-30 13:36:29


