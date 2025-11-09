package com.lee.oa.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.oa.pojo.Employee;
import com.lee.oa.pojo.Response;
import com.lee.oa.pojo.ResponsePage;
import com.lee.oa.service.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


/**
 * @ClassName EmployeeController
 * @Description 员工控制器
 * @Author lihongliang
 * @Date 2025/11/8 10:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Operation(summary = "获取员工列表")
    @GetMapping("/")
    public Response getEmployeeList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    Employee  employee,
                                    LocalDate[] beginDateScope) {
        if (beginDateScope[0].isAfter(beginDateScope[1])) {
            return ResponsePage.error("开始时间 (%s) 不能大于结束时间 (%s)".formatted(beginDateScope[0], beginDateScope[1]));
        }
        IPage<Employee> employees = employeeService.getEmployeesByPage(pageNumber, pageSize, employee, beginDateScope);

        return ResponsePage.success(employees.getTotal(), employees.getRecords());
    }
}
