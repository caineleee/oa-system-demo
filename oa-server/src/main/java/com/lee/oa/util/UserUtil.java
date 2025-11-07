package com.lee.oa.util;

import com.lee.oa.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName UserUtil
 * @Description 用户操作工具类
 * @Author lihongliang
 * @Date 2025/11/5 18:57
 * @Version 1.0
 */
public class UserUtil {

    /**
     * 获取当前登录用户 User 对象
     * @return 当前登录用户
     */
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
