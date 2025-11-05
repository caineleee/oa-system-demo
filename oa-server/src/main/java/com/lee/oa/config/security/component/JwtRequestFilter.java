package com.lee.oa.config.security.component;

import com.lee.oa.service.IUserService;
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
 * 该过滤器拦截所有请求，检查请求头中的JWT Token并进行验证
 * 
 * @author Lee
 * @since 1.0.0
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private IUserService userService;

    /**
     * JWT工具类，用于Token验证和解析
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Token前缀，用于识别JWT Token（Bearer）
     */
    @Value("${jwt.token-start}")
    private String tokenStart;

    /**
     * Token在请求头中的字段名（如"Authorization"）
     */
    @Value("${jwt.token-header}")
    private String tokenHeader;

    /**
     * 过滤请求并验证JWT令牌
     * 对每个请求检查JWT Token，如果有效则设置用户认证信息
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
            // 从Token中提取用户名
            String username = jwtUtil.getUsernameFromToken(jwtToken);

            // 只有当用户名存在且Token有效时才继续处理
            if (username != null && jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 根据用户名加载用户详细信息
                UserDetails userDetails = userService.loadUserByUsername(username);
                // 验证Token是否有效
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