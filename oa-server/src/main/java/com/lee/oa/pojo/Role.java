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
 * @ClassName Role
 * @Description 角色实体类
 * @Author lihongliang
 * @Date 2025/9/27 10:00
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_role")
@ApiModel("角色")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID，主键，自增
     */
    @ApiModelProperty("角色ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @TableField(value = "name")
    private String name;

    /**
     * 角色名称（中文）
     */
    @ApiModelProperty("角色名称（中文）")
    @TableField(value = "name_zh")
    private String nameZh;

    /**
     * 是否启用
     */
    @ApiModelProperty("是否启用")
    @TableField(value = "enabled")
    private Boolean enabled = true;
}