package com.lee.oa.test;

import com.lee.oa.pojo.Employee;
import com.lee.oa.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @ClassName EmployeeDataInitializer
 * @Description 员工测试数据初始化类
 * @Author lihongliang
 * @Date 2025/11/4 15:40
 * @Version 1.0
 */
@SpringBootTest
public class EmployeeDataInitializer {

    @Autowired
    private IEmployeeService employeeService;

    @Test
    public void addEmployeeTestData() {
        String[] firstNames = {"张", "李", "王", "赵", "陈", "刘", "杨", "黄", "周", "吴"};
        String[] lastNames = {"大伟", "芳花", "丽娜", "华敏", "小静", "丽丽", "强强", "研磊", "从军", "海洋",
                "中勇", "美艳", "公杰", "凤娟", "龙涛", "马明", "玉超", "秀", "清霞", "嫒平"};
        String[] genders = {"男", "女"};
        String[] wedlocks = {"已婚", "未婚", "离异"};
        String[] workStates = {"在职", "离职"};
        String[] degrees = {"初中","高中","专科","本科", "硕士", "博士"};
        Integer[] positions = {1, 2,3,4,5,10,11,12,13,14,15,16,17};
        Integer[] departments = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        Integer[] job_level = {1,2,3,4,5,6,7,8,};
        Random random = new Random();

        // 添加30条员工测试数据，不关联测试部门（department_id不使用1或29）
        for (int i = 1; i <= 30; i++) {
            Employee employee = new Employee();
            employee.setName(firstNames[random.nextInt(firstNames.length)] + lastNames[random.nextInt(lastNames.length)]);
            employee.setGender(genders[random.nextInt(genders.length)]);
            employee.setBirthday(LocalDateTime.now().minusYears(25).minusDays(random.nextInt(3650)));
            employee.setIdCard("ID" + String.format("%08d", 10000000 + i));
            employee.setWedlock(wedlocks[random.nextInt(wedlocks.length)]);
            employee.setNationId(random.nextInt(50) + 1);
            employee.setNativePlace("上海");
            employee.setPoliticId(random.nextInt(10) + 1);
            employee.setEmail("employee" + i + "@company.com");
            employee.setPhone("138" + String.format("%08d", random.nextInt(100000000)));
            employee.setAddress("北京市朝阳区某某街道" + (i + 10) + "号");
            
            // 不关联测试部门，使用其他部门ID
            employee.setDepartmentId(departments[random.nextInt(departments.length)]); // 使用30以上的部门ID
            
            employee.setPositionId(positions[random.nextInt(positions.length)]);
            employee.setJobLevelId(job_level[random.nextInt(job_level.length)]);
            employee.setTiptopDegree(degrees[random.nextInt(degrees.length)]);
            employee.setSchool("某某大学");
            employee.setBeginDate(LocalDateTime.now().minusYears(random.nextInt(10)));
            employee.setWorkState(workStates[random.nextInt(workStates.length)]);
            employee.setWorkId("E" + String.format("%06d", 200000 + i));
            employee.setContractTerm(3.0 + random.nextDouble() * 7); // 3-10年合同
            employee.setConversionTime(LocalDateTime.now().minusYears(random.nextInt(5)));
            employee.setBeginContract(LocalDateTime.now().minusYears(random.nextInt(5)));
            employee.setEndContract(LocalDateTime.now().plusYears(random.nextInt(5)).plusMonths(random.nextInt(12)));
            employee.setWorkingYears(random.nextInt(15));
            employee.setSalaryId(random.nextInt(20) + 1);

            employeeService.save(employee);
        }
        
        System.out.println("成功添加30条员工测试数据");
    }
}