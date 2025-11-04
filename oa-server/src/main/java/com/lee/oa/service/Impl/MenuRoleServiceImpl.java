package com.lee.oa.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.oa.mapper.MenuRoleMapper;
import com.lee.oa.pojo.MenuRole;
import com.lee.oa.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName MenuRoleServiceImpl
 * @Description 菜单角色权限服务类
 * @Author lihongliang
 * @Date 2025/11/1 09:45
 * @Version 1.0
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    /**
     * 更新角色访问菜单权限列表
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     * @return 更新结果
     */
    @Override
    @Transactional
    public String updateMenuRole(Integer roleId, List<Integer> menuIds) {
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("role_id", roleId));
        if (menuIds == null || menuIds.isEmpty()) {
            return "更新成功";
        }
        Integer result = menuRoleMapper.insertRecords(roleId, menuIds);
        if (result == menuIds.size()) {
            return "更新成功";
        }
        return "更新失败";
    }
}
