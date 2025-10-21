package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName RoleMapper
 * @Description 角色Mapper接口，用于操作角色相关数据
 * @Author lihongliang
 * @Date 2025/9/27 10:30
 * @Version 1.0
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID获取用户角色列表
     * @param userId 用户ID
     * @return 用户角色列表
     */
    List<Role> getRoles(Integer userId);
}