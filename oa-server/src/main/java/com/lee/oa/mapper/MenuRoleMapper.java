package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.MenuRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
}