package com.lee.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.oa.pojo.Response;
import com.lee.oa.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserService
 * @Description 用户服务接口，定义用户相关业务操作方法
 * @Author lihongliang
 * @Date 2025/9/21 11:02
 * @Version 1.0
 */
public interface UserService extends IService<User>, UserDetailsService {

    /**
     * 用户登录方法
     * 处理用户登录请求，验证用户身份并生成JWT Token
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param request  请求对象，用于获取session信息
     * @return HTTP 响应对应，包含登录结果和token信息
     */
    Response login(String username, String password, String code, HttpServletRequest request);

}