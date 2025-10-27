package com.lee.oa.config.security.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @ClassName CustomUrlDecisionManager
 * @Description 判断用户角色 (权限控制)
 * @Author lihongliang
 * @Date 2025/10/20 10:14
 * @Version 1.0
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {


    /**
     * decide 方法用于判断用户角色是否满足 request URL 权限要求
     * @param authentication 认证信息
     * @param object 请求对象
     * @param configAttributes 配置属性
     * @throws AccessDeniedException 拒绝访问异常
     * @throws InsufficientAuthenticationException  InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            // 当前 URL 所需要的角色
            String needRole = configAttribute.getAttribute();
            // 判断用户角色是否为登录即可访问的角色, 如果是则放行. 此角色在 CustomFilter 中定义
            if ("ROLE_LOGIN".equals(needRole)) {

                // 判断用户是否登录
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("用户尚未登录, 请先登录");
                } else {
                    return;
                }
            }
            // 判断用户角色是否为 URL 所需要的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                // 判断用户角色是否为所需要的角色
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足, 请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
