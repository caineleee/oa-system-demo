package com.lee.oa.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.oa.mapper.NationMapper;
import com.lee.oa.pojo.Nation;
import com.lee.oa.service.INationService;
import org.springframework.stereotype.Service;

/**
 * @ClassName NationService
 * @Description 民族服务实现类
 * @Author lihongliang
 * @Date 2025/11/8 10:15
 * @Version 1.0
 */
@Service
public class NationService extends ServiceImpl<NationMapper, Nation> implements INationService {

}
