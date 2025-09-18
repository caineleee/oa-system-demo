package com.lee.oa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器，用于提供测试接口
 * 
 * @author Lee
 * @since 1.0.0
 */
@RestController
public class TestController {

    /**
     * Hello接口，返回简单的问候语
     * 
     * @return String 问候语
     */
    @GetMapping("/hello")
    public String hello() {
        // 返回问候语
        return "Hello, World!";
    }

    /**
     * 管理员接口，返回管理员问候语
     * 
     * @return String 管理员问候语
     */
    @GetMapping("/admin")
    public String admin() {
        // 返回管理员问候语
        return "Hello, Admin!";
    }
}