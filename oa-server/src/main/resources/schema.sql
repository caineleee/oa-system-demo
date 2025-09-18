-- 创建数据库，如果不存在
CREATE DATABASE IF NOT EXISTS `oa-sys-database`;
-- 使用指定数据库
USE `oa-sys-database`;

-- 创建用户表，如果不存在
CREATE TABLE IF NOT EXISTS `users` (
    -- 用户ID，主键，自动递增
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- 用户名，最大长度50，非空，唯一
    `username` VARCHAR(50) NOT NULL UNIQUE,
    -- 密码，最大长度100，非空
    `password` VARCHAR(100) NOT NULL
);