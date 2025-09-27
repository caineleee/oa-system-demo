package com.lee.oa.config.security;

import com.lee.oa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
     * JWT Token 验证器: 未登录自定义逻辑处理类
     */
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    /**
     * JWT Token 验证器: 无权限自定义逻辑处理类
     */
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    /**
     * 用户详情服务实现类，用于加载用户信息
     */
    @Autowired
    private UserService userService;

    /**
     * JWT请求过滤器，用于验证JWT Token并设置用户认证信息
     */
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * 配置用户详情服务
     * @return UserDetailsService实例
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // 从数据库或其他地方加载用户
            return userService.loadUserByUsername(username);
        };
    }

    /**
     * 配置认证管理器
     * 设置用户详情服务和密码编码器
     * @param auth 认证管理器构建器
     * @throws Exception 配置异常
     */
    public void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        // 设置用户详情服务和密码编码器
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 配置安全过滤器链
     * 设置CSRF保护、会话管理、URL访问权限、JWT过滤器等
     * @param http HttpSecurity对象
     * @return SecurityFilterChain实例
     * @throws Exception 配置异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 禁用CSRF保护（基于JWT Token的无状态认证不需要CSRF保护）
                .csrf().disable()
                // 配置会话管理 : 基于 Token 认证, 不需要 Session.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 配置URL访问权限
                .authorizeRequests()
                // 允许匿名访问的路径
                .antMatchers("/authenticate", "/hello", "/login", "/logout").permitAll()
                // 其他所有请求都需要认证
                .anyRequest().authenticated()
                .and()
                // 禁用缓存控制
                .headers().cacheControl().disable()
                .and()
                // 添加JWT请求过滤器，在用户名密码认证过滤器之前执行
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置异常处理
                .exceptionHandling()
                // 设置访问被拒绝时的处理器
                .accessDeniedHandler(restAccessDeniedHandler)
                // 设置未认证访问时的处理器
                .authenticationEntryPoint(restAuthorizationEntryPoint)
                .and().build();
    }

    /**
     * 配置WebSecurity以忽略Swagger相关路径,以及其他静态资源路径
     * 使用新版Spring Security方式，不继承WebSecurityConfigurerAdapter
     *
     * @return WebSecurityCustomizer实例
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                // Swagger相关路径
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security",
                "/webjars/**",
                // 静态资源路径
                "/css/**",
                "/js/**",
                "/index.html",
                "/favicon.ico",
                // 验证码路径
                "/captcha"
        );
    }
}