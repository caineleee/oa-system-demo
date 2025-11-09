package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.oa.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * @ClassName EmployeeMapper
 * @Description 员工Mapper接口，用于操作员工相关数据
 * @Author lihongliang
 * @Date 2025/11/4 15:35
 * @Version 1.0
 */
@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 分页查询员工列表
     * @param page 分页参数
     * @param employee 查询参数
     * @param beginDateScope 查询范围
     * @return 员工列表
     */
    IPage<Employee> getEmployeesByPage(Page<Employee> page,
                                       @Param("employee") Employee employee,
                                       @Param("beginDateScope") LocalDate[] beginDateScope);
}