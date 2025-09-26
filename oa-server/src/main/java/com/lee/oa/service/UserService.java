package com.lee.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.oa.pojo.Response;
import com.lee.oa.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserService
 * @Description 用户服务接口
 * @Author lihongliang
 * @Date 2025/9/21 11:02
 * @Version 1.0
 */
public interface UserService extends IService<User>, UserDetailsService {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param request  请求对象
     * @return HTTP 响应对应
     */
    Response login(String username, String password, String code, HttpServletRequest request);

}
