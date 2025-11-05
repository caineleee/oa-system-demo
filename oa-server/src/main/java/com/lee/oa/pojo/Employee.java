package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName Employee
 * @Description 员工实体类
 * @Author lihongliang
 * @Date 2025/11/4 15:30
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_employee")
@Schema(name = "Employee", description = "员工信息实体类")
public class Employee implements Serializable {

    @Schema(description = "员工ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "员工姓名")
    @TableField(value = "name")
    private String name;

    @Schema(description = "性别")
    @TableField(value = "gender")
    private String gender;

    @Schema(description = "出生日期")
    @TableField(value = "birthday")
    private LocalDateTime birthday;

    @Schema(description = "身份证号")
    @TableField(value = "id_card")
    private String idCard;

    @Schema(description = "婚姻状况")
    @TableField(value = "wed_lock")
    private String wedlock;

    @Schema(description = "民族ID")
    @TableField(value = "nation_id")
    private Integer nationId;

    @Schema(description = "籍贯")
    @TableField(value = "native_place")
    private String nativePlace;

    @Schema(description = "政治面貌ID")
    @TableField(value = "politic_id")
    private Integer politicId;

    @Schema(description = "邮箱")
    @TableField(value = "email")
    private String email;

    @Schema(description = "电话号码")
    @TableField(value = "phone")
    private String phone;

    @Schema(description = "联系地址")
    @TableField(value = "address")
    private String address;

    @Schema(description = "所属部门ID")
    @TableField(value = "department_id")
    private Integer departmentId;

    @Schema(description = "职位ID")
    @TableField(value = "position_id")
    private Integer positionId;

    @Schema(description = "职称ID")
    @TableField(value = "job_level_id")
    private Integer jobLevelId;

    @Schema(description = "学历专业ID")
    @TableField(value = "tiptop_degree")
    private String tiptopDegree;

    @Schema(description = "毕业院校")
    @TableField(value = "school")
    private String school;

    @Schema(description = "入职日期")
    @TableField(value = "begin_date")
    private LocalDateTime beginDate;

    @Schema(description = "在职状态")
    @TableField(value = "work_state")
    private String workState;

    @Schema(description = "工号")
    @TableField(value = "work_id")
    private String workId;

    @Schema(description = "合同期限")
    @TableField(value = "contract_term")
    private Double contractTerm;

    @Schema(description = "转正日期")
    @TableField(value = "conversion_time")
    private LocalDateTime conversionTime;

    @Schema(description = "离职日期")
    @TableField(value = "discharge_date")
    private LocalDateTime dischargeDate;

    @Schema(description = "合同起始日期")
    @TableField(value = "begin_contract")
    private LocalDateTime beginContract;

    @Schema(description = "合同终止日期")
    @TableField(value = "end_contract")
    private LocalDateTime endContract;

    @Schema(description = "工龄")
    @TableField(value = "working_years")
    private Integer workingYears;

    @Schema(description = "工资ID")
    @TableField(value = "salary_id")
    private Integer salaryId;
}