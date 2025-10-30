package com.lee.oa.controller;

import com.lee.oa.pojo.Menu;
import com.lee.oa.service.MenuService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
//@Api(tags = "菜单控制器")
@Tag(name = "菜单控制器", description = "前端菜单相关接口")
@RestController
@RequestMapping("/system/config")
public class MenuController {

    @Autowired
    private MenuService menuService;

//    @ApiOperation("根据用户 ID 获取菜单列表")
    @Operation(summary = "根据用户 ID 获取菜单列表", description = "根据当前认证用户 ID 获取其可访问的菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusByUserId() {
        return menuService.getMenusByUserId();
    }
}