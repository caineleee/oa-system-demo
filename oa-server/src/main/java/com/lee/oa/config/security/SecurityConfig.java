package com.lee.oa.config.security;

import com.lee.oa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置类，用于配置Spring Security相关设置
 * 
 * @author Lee
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * JWT Token 验证器: 未登录自定义逻辑
     */
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    /**
     * JWT Token 验证器: 无权限自定义逻辑
     */
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    /**
     * 用户详情服务实现类
     */
    @Autowired
    private UserService userService;

    /**
     * JWT请求过滤器
     */
    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // 从数据库或其他地方加载用户
            return userService.loadUserByUsername(username);
        };
    }


    public void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        // 设置用户详情服务和密码编码器
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) throws Exception {
        return http
                // 禁用CSRF保护
                .csrf().disable()
                // 配置会话管理 : 基于 Token 认证, 不需要 Session.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/authenticate", "/hello", "/login", "/logout").permitAll()
                .antMatchers("/swagger-ui.html"//,
//                        "/webjars/**",
//                        "/swagger-resources/**",
//                        "/v2/api-docs",
//                        "/swagger-resources/configuration/ui",
//                        "/swagger-resources/configuration/security"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().cacheControl().disable()
                .and()
                // JWT 登录授权过滤器
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint)
                .and().build();
    }

//    /**
//     * 密码编码器Bean
//     *
//     * @return PasswordEncoder 密码编码器
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // 返回BCrypt密码编码器实例
//        return new BCryptPasswordEncoder();
//     }
}