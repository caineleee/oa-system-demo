package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

}