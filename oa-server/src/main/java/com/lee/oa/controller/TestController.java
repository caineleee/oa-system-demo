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

    @GetMapping("/employee/basic/hello")
    public String helloEmployee() {
        // 默认接口，返回用户ello
        return "/employee/basic/hello";
    }

    @GetMapping("/employee/advanced/hello")
    public String helloAdvanced() {
        // 默认接口，返回用户ello
        return "/employee/advanced/hello";
    }


}