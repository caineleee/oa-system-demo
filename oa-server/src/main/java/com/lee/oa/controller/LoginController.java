package com.lee.oa.controller;

import com.lee.oa.pojo.LoginInfo;
import com.lee.oa.pojo.Response;
import com.lee.oa.pojo.User;
import com.lee.oa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @ClassName LoginController
 * @Description 登录控制器
 * @Author lihongliang
 * @Date 2025/9/21 10:40
 * @Version 1.0
 */
@Api(tags = "登录控制器")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Response login(@RequestBody LoginInfo info, HttpServletRequest  request) {
        return userService.login(info.getUsername(), info.getPassword(), request);
    }

    @ApiOperation("用户退出登录")
    @PostMapping("/logout")
    public Response logout() {
        return Response.success("退出登录成功");
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/getCurrentUser")
    public User getCurrentUser(Principal  principal) {
        if (principal == null) {
            return null;
        }
        String username = principal.getName();
        User user = (User) userService.loadUserByUsername(username);
        user.setPassword(null);
        return user;
    }
}
