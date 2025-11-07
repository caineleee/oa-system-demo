package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UserRoleMapper
 * @Description 用户角色Mapper接口，用于操作用户角色关联数据
 * @Author lihongliang
 * @Date 2025/9/27 10:35
 * @Version 1.0
 */
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 添加管理员角色
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 更新结果
     */
    Integer AddUserRoles(@Param("userId") Integer userId,
                        @Param("roleIds") List<Integer> roleIds);
}