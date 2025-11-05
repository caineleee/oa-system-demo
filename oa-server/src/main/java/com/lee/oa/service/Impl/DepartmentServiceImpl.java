package com.lee.oa.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.oa.mapper.DepartmentMapper;
import com.lee.oa.pojo.Department;
import com.lee.oa.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DepartmentServiceImpl
 * @Description 部门服务实现类
 * @Author lihongliang
 * @Date 2025/11/4 11:34
 * @Version 1.0
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门 (MyBatis 递归查询)
     * @return 部门列表
     */
    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }

    /**
     * 添加部门
     * @param department 部门对象
     * @return 添加结果
     */
    @Override
    public String addDepartment(Department department) {
        department.setEnabled(true);
        department.setIsParent(department.getChildren() != null);
        departmentMapper.addDepartment(department);
        if (department.getResult() == 1) {
            return "添加成功";
        }
        return "添加失败";
    }

    /**
     * 删除部门
     * @param id 部门ID
     * @return 删除结果
     */
    @Override
    public String deleteDepartment(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        if (department.getResult() == -2) {
            return "该部门下还有子部门,删除失败";
        } else if (department.getResult() == -1) {
            return "该部门下还有员工,删除失败";
        } else if (department.getResult() == 1) {
            return "删除成功";
        }
        return "删除失败";
    }
}
