package com.lee.oa.config.security;

import com.lee.oa.config.security.component.CustomFilter;
import com.lee.oa.config.security.component.CustomUrlDecisionManager;
import com.lee.oa.config.security.component.JwtRequestFilter;
import com.lee.oa.config.security.component.RestAccessDeniedHandler;
import com.lee.oa.config.security.component.RestAuthorizationEntryPoint;
import com.lee.oa.pojo.User;
import com.lee.oa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
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
     * 自定义过滤器, 获取当前请求需要的角色 (权限控制)
     */
    @Autowired
    private CustomFilter customFilter;

    /**
     *  自定义权限管理器, 用于判断用户是否具有访问某个URL的权限
     */
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;

    /**
     * 配置用户详情服务
     * @return UserDetailsService实例
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // 从数据库或其他地方加载用户
            User user = (User) userService.loadUserByUsername(username);
            if (user != null) {
                // 加载用户角色信息
                user.setRoles(userService.getRoles(user.getId().intValue()));
                return user;
            }
            throw new UsernameNotFoundException("用户或密码不正确");
        };
    }

    /**
     * 配置认证管理器
     * 设置用户详情服务和密码编码器
     * @param http HttpSecurity对象
     * @throws Exception 配置异常
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(new BCryptPasswordEncoder())
                .and()
                .build();
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
                .antMatchers("/hello", "/login", "/logout").permitAll()
                // 其他所有请求都需要认证
                .anyRequest().authenticated()
                // 动态权限配置, 用户预判请求和用户的角色权限是否匹配
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        object.setSecurityMetadataSource(customFilter);
                        return object;
                    }
                })
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
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security",
                "/webjars/**",
                // 静态资源路径
                "/css/**",
                "/js/**",
                "/index.html",
                "/favicon.ico",
                // 验证码路径
                "/captcha",
                // 登录路径
                "/login",
                "/logout",
                // 错误页面路径
                "/error",
                "/hello"
        );
    }
}