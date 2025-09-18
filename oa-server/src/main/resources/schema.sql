CREATE DATABASE IF NOT EXISTS `oa-sys-database`;
USE `oa-sys-database`;

CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(100) NOT NULL
);