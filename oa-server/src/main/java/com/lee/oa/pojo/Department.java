package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName Department
 * @Description
 * @Author lihongliang
 * @Date 2025/11/4 11:28
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_department")
@Schema(name = "Department", description = "部门实体类")
public class Department implements Serializable {

    @Schema(description = "部门ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "上级部门 ID")
    @TableField(value = "parent_id")
    private Integer parentId;

    @Schema(description = "部门层级")
    @TableField(value = "department_path")
    private String departmentPath;

    @Schema(description = "是否可用")
    private Boolean enabled;

    @Schema(description = "是否有子部门")
    @TableField(value = "is_parent")
    private Boolean isParent;

    @Schema(description = "子部门")
    @TableField(exist = false)
    private List<Department> children;

    @Schema(description = "返回结果, 存储过程中使用")
    @TableField(exist = false)
    private int result;
}
