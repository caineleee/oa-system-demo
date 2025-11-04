package com.lee.oa.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.oa.mapper.RoleMapper;
import com.lee.oa.pojo.Role;
import com.lee.oa.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * @ClassName RoleService
 * @Description 角色服务类
 * @Author lihongliang
 * @Date 2025/10/31 18:12
 * @Version 1.0
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
