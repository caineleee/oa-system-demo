package com.lee.oa.controller;

import com.lee.oa.pojo.Menu;
import com.lee.oa.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName MenuController
 * @Description 前端菜单控制器
 * @Author lihongliang
 * @Date 2025/9/26 17:37
 * @Version 1.0
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("根据用户 ID 获取菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusByUserId() {
        return menuService.getMenusByUserId();
    }
}
