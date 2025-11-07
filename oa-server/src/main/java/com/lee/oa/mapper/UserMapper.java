package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * 获取所有管理员
     * @param id 当前用户ID
     * @param keywords 关键字
     * @return 管理员列表
     */
    List<User> getAdmins(@Param("id") Long id, @Param("keywords") String keywords);
}