package com.lee.oa.config.security;

import com.lee.oa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT请求过滤器，用于验证JWT令牌并设置用户认证信息
 * 
 * @author Lee
 * @since 1.0.0
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    /**
     * JWT工具类
     */
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.token-start}")
    private String tokenStart;

    @Value("${jwt.token-header}")
    private String tokenHeader;

    /**
     * 过滤请求并验证JWT令牌
     * 
     * @param request HTTP请求
     * @param response HTTP响应
     * @param chain 过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // 从请求头中获取Authorization字段
        String requestTokenHeader = request.getHeader(tokenHeader);

        // JWT Token的格式为"Bearer token"，因此要提取实际的token
        if (requestTokenHeader != null && requestTokenHeader.startsWith(tokenStart)) {
            // 提取JWT令牌（去掉"Bearer "前缀）
            String jwtToken = requestTokenHeader.substring(tokenStart.length());
            String username = jwtUtil.getUsernameFromToken(jwtToken);

            // Token 存在但是未登录
            if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (userDetails != null && jwtUtil.validateToken(jwtToken, userDetails)) {
                    // 创建用户名密码认证令牌
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            // 获取用户详情
                            userDetails,
                            // 获取凭证(密码)为null
                            null,
                            // 获取用户权限
                            userDetails.getAuthorities());
                    // 设置认证详情
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 将认证信息设置到安全上下文中
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        }
        // 继续执行过滤器链
        chain.doFilter(request, response);
    }
}