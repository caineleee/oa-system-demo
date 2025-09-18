package com.lee.oa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OA系统主应用启动类
 * 
 * @author Lee
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("com.lee.oa.mapper")
public class OaSystemApplication {
    /**
     * 应用程序入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动Spring Boot应用程序
        SpringApplication.run(OaSystemApplication.class, args);
    }
}