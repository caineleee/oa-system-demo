package com.lee.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.oa.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName DepartmentMapper
 * @Description 部门Mapper接口，用于操作部门相关数据
 * @Author lihongliang
 * @Date 2025/11/4 11:37
 * @Version 1.0
 */
@Mapper
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取所有部门 (MyBatis 递归查询)
     * @param parentId 上级部门ID
     * @return 部门列表
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 添加部门
     * @param department 部门信息
     */
    void addDepartment(Department department);

    /**
     * 删除部门
     * @param department 部门信息
     */
    void deleteDepartment(Department department);
}
