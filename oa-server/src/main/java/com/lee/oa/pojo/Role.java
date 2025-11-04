package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
//@ApiModel("角色")
@Schema(name = "Role", description = "角色信息实体类")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID，主键，自增
     */
    //@ApiModelProperty("角色ID")
    @Schema(description = "角色ID，主键，自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    //@ApiModelProperty("角色名称")
    @Schema(description = "角色名称")
    @TableField(value = "name")
    private String name;

    /**
     * 角色名称（中文）
     */
    //@ApiModelProperty("角色名称（中文）")
    @Schema(description = "角色名称（中文）")
    @TableField(value = "name_zh")
    private String nameZh;

    /**
     * 是否启用
     */
    //@ApiModelProperty("是否启用")
    @Schema(description = "是否启用")
    @TableField(value = "enabled")
    private Boolean enabled = true;
}