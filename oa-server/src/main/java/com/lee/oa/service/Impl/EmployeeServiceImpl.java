package com.lee.oa.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.oa.mapper.EmployeeMapper;
import com.lee.oa.pojo.Employee;
import com.lee.oa.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @ClassName EmployeeServiceImpl
 * @Description 员工服务类
 * @Author lihongliang
 * @Date 2025/11/4 15:38
 * @Version 1.0
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 获取员工列表
     * @param pageNumber 页码
     * @param pageSize 页大小
     * @param employee 查询参数
     * @param beginDateScope 查询日期范围
     * @return 员工列表
     */
    @Override
    public IPage<Employee> getEmployeesByPage(Integer pageNumber, Integer pageSize,
                                              Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(pageNumber, pageSize);
        return employeeMapper.getEmployeesByPage(page, employee, beginDateScope);
    }
}