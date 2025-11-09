package com.lee.oa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.oa.pojo.Employee;

import java.time.LocalDate;

/**
 * @ClassName IEmployeeService
 * @Description 员工服务接口
 * @Author lihongliang
 * @Date 2025/11/4 15:37
 * @Version 1.0
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取员工列表
     * @param pageNumber 页码
     * @param pageSize 页大小
     * @param employee 查询参数
     * @param beginDateScope 查询日期范围
     * @return 员工列表
     */
    IPage<Employee> getEmployeesByPage(Integer pageNumber, Integer pageSize,
                             Employee employee, LocalDate[] beginDateScope);
}