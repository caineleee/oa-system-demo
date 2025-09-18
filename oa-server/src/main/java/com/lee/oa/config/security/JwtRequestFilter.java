package com.lee.oa.config.security;

import com.lee.oa.service.UserDetailsServiceImpl;
import com.lee.oa.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 用户详情服务实现类
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * JWT工具类
     */
    @Autowired
    private JwtUtil jwtUtil;

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
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // JWT Token的格式为"Bearer token"，因此要提取实际的token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            // 提取JWT令牌（去掉"Bearer "前缀）
            jwtToken = requestTokenHeader.substring(7);
            try {
                // 从JWT令牌中提取用户名
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                // 无法获取JWT令牌时的处理
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                // JWT令牌过期时的处理
                System.out.println("JWT Token has expired");
            }
        } else {
            // JWT令牌不是以Bearer开头时记录警告日志
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // 验证token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 根据用户名加载用户详情
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 验证JWT令牌是否有效
            if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
                // 创建用户名密码认证令牌
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        // 设置用户详情
                        userDetails, 
                        // 设置凭证为null
                        null, 
                        // 设置用户权限
                        userDetails.getAuthorities());
                // 设置认证详情
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将认证信息设置到安全上下文中
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 继续执行过滤器链
        chain.doFilter(request, response);
    }
}