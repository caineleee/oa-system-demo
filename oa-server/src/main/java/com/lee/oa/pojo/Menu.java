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
import java.util.List;

/**
 * @ClassName Menu
 * @Description 菜单栏实体类
 * @Author lihongliang
 * @Date 2025/9/26 17:27
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_menu")
@ApiModel("菜单")
public class Menu implements Serializable {

    @ApiModelProperty("菜单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("菜单URL")
    private String url;

    @ApiModelProperty("菜单组件")
    private String component;

    @ApiModelProperty("图标")
    @TableField(value = "icon_cls")
    private String icon;

    @ApiModelProperty("是否激活")
    @TableField(value = "keep_alive")
    private Boolean keepAlive;

    @ApiModelProperty("是否需要权限")
    @TableField(value = "require_auth")
    private Boolean requireAuth;

    @ApiModelProperty("父级ID")
    @TableField(value = "parent_id")
    private Long parentId;

    @ApiModelProperty("是否启用")
    private Boolean enabled;

    @ApiModelProperty("子菜单")
    @TableField(exist = false)
    private List<Menu> children;

}
