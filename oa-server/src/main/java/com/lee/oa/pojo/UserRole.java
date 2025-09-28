package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName UserRole
 * @Description 用户角色关联实体类
 * @Author lihongliang
 * @Date 2025/9/27 10:10
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_role")
@ApiModel("用户角色关联")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID，自增
     */
    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    @TableField(value = "role_id")
    private Long roleId;
}