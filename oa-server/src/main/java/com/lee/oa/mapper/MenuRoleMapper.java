package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.MenuRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName MenuRoleMapper
 * @Description 菜单角色Mapper接口，用于操作菜单角色关联数据
 * @Author lihongliang
 * @Date 2025/9/27 10:37
 * @Version 1.0
 */
@Mapper
@Repository
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 批量更新角色菜单关系
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     * @return 更新的记录数
     */
    Integer insertRecords(@Param("roleId") Integer roleId,
                          @Param("menuIds") List<Integer> menuIds);
}