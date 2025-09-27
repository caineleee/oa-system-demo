package com.lee.oa.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.oa.config.security.JwtUtil;
import com.lee.oa.mapper.UserMapper;
import com.lee.oa.pojo.Response;
import com.lee.oa.pojo.User;
import com.lee.oa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Description 用户登录逻辑实现类，负责处理用户登录、认证和JWT Token生成
 * @Author lihongliang
 * @Date 2025/9/21 11:06
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 用户Mapper对象，用于数据库操作
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * JWT 工具类对象，用于生成和验证JWT Token
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * JWT Token 的请求头名称，用于构建返回给前端的token信息
     */
    @Value("${jwt.token-start}")
    private String tokenStart;

    /**
     * 用户登录验证并生成JWT Token
     * 该方法处理用户登录的完整流程：
     * 1. 验证验证码
     * 2. 验证用户名和密码
     * 3. 检查用户是否被禁用
     * 4. 生成新的JWT Token
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param request  HTTP请求对象，用于获取session中的验证码
     * @return Response 响应结果，包含token信息或错误信息
     */
    @Override
    public Response login(String username, String password, String code, HttpServletRequest request) {
        // 从session中获取验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        // 验证验证码是否正确（包括null检查）
        if (captcha == null || captcha.isEmpty() || !captcha.equalsIgnoreCase(code)) {
            return Response.error("验证码错误, 请重新输入");
        }
        // 通过用户名加载用户详细信息
        UserDetails userDetails = loadUserByUsername(username);
        // 验证用户是否存在或密码是否正确
        if (userDetails == null || !new BCryptPasswordEncoder().matches(password, userDetails.getPassword())) {
            // 用户名或密码错误时返回错误信息
            return Response.error("用户名或密码错误");
        }
        // 检查用户是否被禁用
        if (!userDetails.isEnabled()) {
            // 用户被禁用时返回错误信息
            return Response.error("用户账号被禁用, 请联系管理员");
        }
        // 验证JWT Token是否有效
        if (request.getHeader("Authorization") != null
                && !jwtUtil.validateToken(request.getHeader("Authorization"), userDetails)) {
            // Token过期时返回错误信息
            return Response.error("Token 已过期, 请重新登录");
        }

        // 创建 Spring Security 认证对象，包含用户信息和权限列表
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        // 将认证信息存储到安全上下文中 (Spring 自动线程隔离,多用户登录不会冲突)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成新的JWT Token
        String token = jwtUtil.generateToken(userDetails);
        // 构造包含token和token头部信息的Map
        Map<String, String> map = Map.of("token", token, "tokenStart", tokenStart);
        // 返回登录成功的响应信息
        return Response.success("登录成功", map);
    }


    /**
     * 根据用户名加载用户详细信息
     * @param username 用户名
     * @return 用户详细信息
     * @throws UsernameNotFoundException 用户名不存在时抛出异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 构造查询条件，查询用户名匹配且启用状态为true的用户
        return userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", username)
                .eq("enabled", true));
    }
}