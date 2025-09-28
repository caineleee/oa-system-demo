package com.lee.oa.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.oa.mapper.MenuMapper;
import com.lee.oa.pojo.Menu;
import com.lee.oa.pojo.User;
import com.lee.oa.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    /**
     * 根据用户 ID, 获取用户可以访问的菜单列表
     * @return 用户菜单列表
     */
    @Override
    public List<Menu> getMenusByUserId() {
        return menuMapper.getMenusByUserId(
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

}
