package com.lee.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.oa.pojo.MenuRole;

import java.util.List;

/**
 * @ClassName IMenuRoleService
 * @Description 菜单角色权限服务接口
 * @Author lihongliang
 * @Date 2025/11/1 09:41
 * @Version 1.0
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色访问菜单权限列表
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     * @return 更新结果
     */
    String updateMenuRole(Integer roleId, List<Integer> menuIds);
}
