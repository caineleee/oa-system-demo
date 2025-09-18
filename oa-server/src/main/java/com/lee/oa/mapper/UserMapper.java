package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户Mapper接口，用于操作用户相关数据
 * 
 * @author Lee
 * @since 1.0.0
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户对象
     */
    default Optional<User> findByUsername(String username) {
        // 使用LambdaQueryWrapper构建查询条件
        return selectList(
                // 创建LambdaQueryWrapper并设置查询条件
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                        // 设置用户名等于查询参数的条件
                        .eq(User::getUsername, username))
                // 将查询结果转换为流并获取第一个元素
                .stream()
                // 获取第一个元素，如果没有则返回空Optional
                .findFirst();
    }
}