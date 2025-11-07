package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName MenuMapper
 * @Description
 * @Author lihongliang
 * @Date 2025/9/28 11:23
 * @Version 1.0
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户 ID, 获取用户可以访问的菜单列表
     * @return 用户菜单列表
     */
    List<Menu> getMenusByUserId(Long id);

    /**
     * 根据当前角色获取菜单
     * @return 当前角色可以访问的菜单列表
     */
    List<Menu> getMenusWithRole();

    /**
     * 获取所有菜单
     * @return 所有菜单列表
     */
    List<Menu> getAllMenus();
}
