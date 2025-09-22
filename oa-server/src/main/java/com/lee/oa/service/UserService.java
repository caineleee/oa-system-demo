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
     * @param username
     * @param password
     * @param request
     * @return
     */
    Response login(String username, String password, HttpServletRequest request);

//    /**
//     * 根据用户名获取用户信息
//     * @param username
//     * @return
//     */
//    User findByUsername(String username);
}
