package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
}