package com.lee.oa.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.oa.mapper.EmployeeMapper;
import com.lee.oa.pojo.Employee;
import com.lee.oa.service.IEmployeeService;
import org.springframework.stereotype.Service;

/**
 * @ClassName EmployeeServiceImpl
 * @Description 员工服务类
 * @Author lihongliang
 * @Date 2025/11/4 15:38
 * @Version 1.0
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}