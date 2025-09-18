package com.lee.oa.config.security;

import com.lee.oa.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * JWT认证入口点
     */
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * 用户详情服务实现类
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * JWT请求过滤器
     */
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * 密码编码器Bean
     * 
     * @return PasswordEncoder 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 返回BCrypt密码编码器实例
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证管理器
     * 
     * @param auth 认证管理器构建器
     * @throws Exception 配置异常
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置用户详情服务和密码编码器
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 认证管理器Bean
     * 
     * @return AuthenticationManager 认证管理器
     * @throws Exception 异常
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // 调用父类方法获取认证管理器
        return super.authenticationManagerBean();
    }

    /**
     * 配置HTTP安全策略
     * 
     * @param http HTTP安全配置
     * @throws Exception 配置异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用CSRF保护
        http.csrf().disable()
                // 配置请求授权规则
                .authorizeRequests()
                // 允许所有用户访问认证和hello接口
                .antMatchers("/authenticate", "/hello").permitAll()
                // 其他所有请求都需要认证
                .anyRequest().authenticated()
                // 配置异常处理
                .and()
                // 设置认证入口点
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // 配置会话管理
                .and()
                // 设置会话创建策略为无状态
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 在用户名密码认证过滤器之前添加JWT请求过滤器
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}