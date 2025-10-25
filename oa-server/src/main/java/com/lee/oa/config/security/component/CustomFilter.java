package com.lee.oa.config.security.component;

import com.lee.oa.pojo.Menu;
import com.lee.oa.pojo.Role;
import com.lee.oa.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName CustomFilter
 * @Description 基于 request URL 分析请求所需角色 (权限控制)
 * @Author lihongliang
 * @Date 2025/10/19 09:20
 * @Version 1.0
 */

@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 获取当前请求需要的角色 (权限控制)
     * @param object 请求对象
     * @return 角色 (权限控制)
     * @throws IllegalArgumentException 抛出
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求的 URL (object 就是当前请求 FilterInvocation 对象, 可以直接获取请求信息)
        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        // 获取当前认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 检查是否有有效的认证信息
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            // 如果没有有效的认证信息，返回默认角色
            return SecurityConfig.createList("ROLE_LOGIN");
        }

        List<Menu> menus = menuService.getMenusByUserId();
        for (Menu menu : menus) {
            // 判断请求 URL 与 菜单 URL 是否匹配
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                // 获取角色数组
                String[] roles = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                // 将角色名称或权限标识封装成 Spring Security 可识别的安全配置对象
                return SecurityConfig.createList(roles);
            }
        }
        // 如果没有找到匹配的菜单，返回默认角色
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return List.of();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
