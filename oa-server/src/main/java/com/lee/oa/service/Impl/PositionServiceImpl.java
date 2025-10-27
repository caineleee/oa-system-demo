package com.lee.oa.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.oa.mapper.PositionMapper;
import com.lee.oa.pojo.Position;
import com.lee.oa.service.IPositionService;
import org.springframework.stereotype.Service;

/**
 * @ClassName PositionServiceImpl
 * @Description 职位服务类, 定义职位相关业务操作方法
 * @Author lihongliang
 * @Date 2025/10/27 11:05
 * @Version 1.0
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

}
