package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.PoliticsStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName PoliticsStatusMapper
 * @Description 政治面貌查询接口
 * @Author lihongliang
 * @Date 2025/11/8 10:30
 * @Version 1.0
 */
@Mapper
@Repository
public interface PoliticsStatusMapper extends BaseMapper<PoliticsStatus> {

}
