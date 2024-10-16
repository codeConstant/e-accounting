CREATE DATABASE IF NOT EXISTS user;
USE user;

DROP TABLE IF EXISTS `user_tbl`;

CREATE TABLE `user_tbl` (
                    `id` bigint NOT NULL AUTO_INCREMENT,
                    `address` varchar(255) DEFAULT NULL,
                    `date_of_birth` date DEFAULT NULL,
                    `email` varchar(255) NOT NULL,
                    `first_name` varchar(255) DEFAULT NULL,
                    `gender` enum('FEMALE','MALE') DEFAULT NULL,
                    `given_name` varchar(255) DEFAULT NULL,
                    `password` varchar(255) DEFAULT NULL,
                    `phone_number` varchar(255) NOT NULL,
                    PRIMARY KEY (`id`),
                    UNIQUE KEY `UKi4ygcc30htflmb5xe5mjcydid` (`email`),
                    UNIQUE KEY `UKd9nr79diyvrfbbu38469m3t3h` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=ujis;

insert  into `user_tbl`(`id`,`address`,`date_of_birth`,`email`,`first_name`,`gender`,`given_name`,`password`,`phone_number`) values
(1,'Dhaka','1986-08-10','arefin@gmail.com','Samsul','MALE','Arefin',NULL,'01912628475'),
(2,'Dhaka','1990-08-10','Khan@gmail.com','Mamun','MALE','Khan',NULL,'01895623785'),
(3,'Dhaka','1999-08-10','jarif@gmail.com','Jarif','MALE','Imtiaz',NULL,'01895623780'),
(4,'Dhaka','1985-08-10','mehedi@gmail.com','Mehedi','MALE','Salim',NULL,'01895623789');



DROP TABLE IF EXISTS `customer_account_tbl`;

CREATE TABLE `customer_account_tbl` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `account_number` varchar(255) NOT NULL,
                            `account_status` enum('ACTIVE','INACTIVE') DEFAULT NULL,
                            `account_type` enum('CREDIT','FIXED_DEPOSIT','LOAN','SAVING') DEFAULT NULL,
                            `balance` decimal(38,2) DEFAULT NULL,
                            `date_of_birth` date DEFAULT NULL,
                            `full_name` varchar(255) DEFAULT NULL,
                            `last_transaction_date` date DEFAULT NULL,
                            `user_id` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UKedt38wilrgixkxtkibuw8ikvi` (`account_number`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=ujis;


insert  into `customer_account_tbl`(`id`,`account_number`,`account_status`,`account_type`,`balance`,`date_of_birth`,`full_name`,`last_transaction_date`,`user_id`) values
(1,'34243424ABGTY678RT45','ACTIVE','SAVING',10000,'1986-08-10','Samsul Arefin',NULL,1),
(2,'56892233363665','ACTIVE','SAVING',10000,'1990-08-10','Mamun Khan',NULL,2);
