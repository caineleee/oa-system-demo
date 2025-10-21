package com.lee.oa.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.oa.mapper.MenuMapper;
import com.lee.oa.pojo.Menu;
import com.lee.oa.pojo.User;
import com.lee.oa.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName MenuServiceImpl
 * @Description 菜单服务接口, 定义菜单相关业务操作方法
 * @Author lihongliang
 * @Date 2025/9/28 11:19
 * @Version 1.0
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据用户 ID, 获取用户可以访问的菜单列表
     * @return 用户菜单列表
     */
    @Override
    public List<Menu> getMenusByUserId() {
        Long userId =  ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 从 redis 获取菜单数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + userId);
        // 如果为空则从数据库获取
        if (CollectionUtils.isEmpty(menus)) {
            menus = menuMapper.getMenusByUserId(userId);
            // 从数据库获取的数据, 存入 redis
            valueOperations.set("menu_" + userId, menus);
        }
        return menus;

    }

    /**
     * 根据当前用户角色获取菜单
     * @return 菜单列表
     */
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

}
