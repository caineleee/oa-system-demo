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
 * @ClassName MenuRole
 * @Description 菜单角色关联实体类
 * @Author lihongliang
 * @Date 2025/9/27 10:15
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_menu_role")
//@ApiModel("菜单角色关联")
@Schema(name = "MenuRole", description = "菜单角色关联信息实体类")
public class MenuRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID，自增
     */
    //@ApiModelProperty("主键ID")
    @Schema(description = "主键ID，自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单ID
     */
    //@ApiModelProperty("菜单ID")
    @Schema(description = "菜单ID")
    @TableField(value = "menu_id")
    private Long menuId;

    /**
     * 角色ID
     */
    //@ApiModelProperty("角色ID")
    @Schema(description = "角色ID")
    @TableField(value = "role_id")
    private Long roleId;
}