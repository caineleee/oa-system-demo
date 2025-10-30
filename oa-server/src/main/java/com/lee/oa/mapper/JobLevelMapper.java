package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.JobLevel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName JobLevelMapper
 * @Description
 * @Author lihongliang
 * @Date 2025/10/29 10:15
 * @Version 1.0
 */
@Mapper
@Repository
public interface JobLevelMapper extends BaseMapper<JobLevel> {

}
