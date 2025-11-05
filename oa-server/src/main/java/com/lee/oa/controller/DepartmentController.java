package com.lee.oa.controller;

import com.lee.oa.pojo.Department;
import com.lee.oa.pojo.Response;
import com.lee.oa.service.IDepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DepartmentController
 * @Description 部门控制器
 * @Author lihongliang
 * @Date 2025/11/4 11:21
 * @Version 1.0
 */
@RestController
@Tag(name = "部门控制器")
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @Operation(summary = "获取所有部门")
    @GetMapping("/")
    public Response getAllDepartments() {
        return Response.success("access", departmentService.getAllDepartments());
//        return Response.success("access", departmentService.list());
    }

    @Operation(summary = "添加部门")
    @PostMapping("/")
    public Response addDepartment(@RequestBody Department department) {
        String reuslt = departmentService.addDepartment(department);
        if ("添加成功".equals(reuslt)) {
            return Response.success(reuslt);
        }
        return Response.error(reuslt);
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{id}")
    public Response deleteDepartment(@PathVariable Integer id) {
        String reuslt = departmentService.deleteDepartment(id);
        if ("删除成功".equals(reuslt)) {
            return Response.success(reuslt);
        }
        return Response.error(reuslt);
    }

}
