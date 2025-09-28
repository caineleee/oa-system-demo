package com.lee.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.oa.pojo.Menu;

import java.util.List;

/**
 * @ClassName MenuService
 * @Description 菜单服务接口, 定义菜单相关业务操作方法
 * @Author lihongliang
 * @Date 2025/9/28 11:14
 * @Version 1.0
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取当前用户菜单
     * @return 当前用户可以访问的菜单列表
     */
    List<Menu> getMenusByUserId();
}
