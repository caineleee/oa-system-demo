package com.lee.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.oa.pojo.Department;

import java.util.List;

/**
 * @ClassName IDepartmentService
 * @Description 部门服务接口
 * @Author lihongliang
 * @Date 2025/11/4 11:27
 * @Version 1.0
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门 (MyBatis 递归查询)
     * @return 部门列表
     */
    List<Department> getAllDepartments();

    /**
     * 添加部门
     * @param department 部门对象
     * @return 添加结果
     */
    String addDepartment(Department department);

    /**
     * 删除部门
     * @param id 部门id
     * @return 删除结果
     */
    String deleteDepartment(Integer id);
}
