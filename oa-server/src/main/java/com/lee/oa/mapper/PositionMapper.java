package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.Position;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName PositionMapper
 * @Description 职位数据操作方法
 * @Author lihongliang
 * @Date 2025/10/27 11:07
 * @Version 1.0
 */
@Mapper
@Repository
public interface PositionMapper extends BaseMapper<Position> {

}
