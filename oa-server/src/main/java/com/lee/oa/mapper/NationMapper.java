package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.Nation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName NationMapper
 * @Description 民族数据操作接口
 * @Author lihongliang
 * @Date 2025/11/8 10:16
 * @Version 1.0
 */
@Mapper
@Repository
public interface NationMapper extends BaseMapper<Nation> {

}
