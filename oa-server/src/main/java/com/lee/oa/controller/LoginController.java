package com.lee.oa.controller;

import com.lee.oa.pojo.LoginInfo;
import com.lee.oa.pojo.Response;
import com.lee.oa.pojo.User;
import com.lee.oa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @ClassName LoginController
 * @Description 登录控制器，处理用户登录、退出和获取当前用户信息等请求
 * @Author lihongliang
 * @Date 2025/9/21 10:40
 * @Version 1.0
 */
@Api(tags = "登录控制器")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     * 接收用户名、密码和验证码，验证通过后生成JWT Token
     * @param info 登录信息（包含用户名、密码和验证码）
     * @param request HTTP请求对象
     * @return 登录结果响应
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Response login(@RequestBody LoginInfo info, HttpServletRequest  request) {
        // 调用UserService的登录方法进行用户认证
        return userService.login(info.getUsername(), info.getPassword(), info.getCode(), request);
    }

    /**
     * 用户退出登录接口
     * @return 退出登录结果响应
     */
    @ApiOperation("用户退出登录")
    @PostMapping("/logout")
    public Response logout() {
        // 直接返回退出登录成功的响应（实际的token清除在前端完成）
        return Response.success("退出登录成功");
    }

    /**
     * 获取当前用户信息接口
     * @param principal 当前认证用户信息
     * @return 当前用户信息
     */
    @ApiOperation("获取当前用户信息")
    @GetMapping("/user")
    public User getCurrentUser(Principal  principal) {
        // 检查用户是否已认证
        if (principal == null) {
            return null;
        }
        // 获取用户名
        String username = principal.getName();
        // 根据用户名加载用户详细信息
        User user = (User) userService.loadUserByUsername(username);
        // 清除密码信息，避免密码泄露
        user.setPassword(null);
        // 加载用户角色信息
        user.setRoles(userService.getRoles(user.getId().intValue()));
        return user;
    }
}