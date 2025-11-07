package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户实体类，对应数据库中的t_user表
 * 实现UserDetails接口，用于Spring Security认证
 * 
 * @author Lee
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_user")
//@ApiModel(value = "用户实体类")
@Schema(name = "User", description = "用户信息实体类")
public class User implements Serializable, UserDetails {
    /**
     * 用户ID，主键，自增
     */
    //@ApiModelProperty("用户ID")
    @Schema(description = "用户ID，主键，自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名，用于登录
     */
    //@ApiModelProperty("用户名")
    @Schema(description = "用户名，用于登录")
    @TableField(value = "username")
    private String username;

    /**
     * 密码，加密存储
     */
    //@ApiModelProperty("密码")
    @Schema(description = "密码，加密存储")
    @TableField(value = "password")
    private String password;

    /**
     * 姓名，用户的真实姓名
     */
    //@ApiModelProperty("姓名")
    @Schema(description = "姓名，用户的真实姓名")
    @TableField(value = "name")
    private String name;

    /**
     * 邮箱，用户的电子邮箱地址
     */
    //@ApiModelProperty("邮箱")
    @Schema(description = "邮箱，用户的电子邮箱地址")
    @TableField(value = "email")
    private String email;

    /**
     * 手机，用户的手机号码
     */
    //@ApiModelProperty("手机")
    @Schema(description = "手机，用户的手机号码")
    @TableField(value = "phone")
    private String phone;

    /**
     * 座机号，用户的座机号码
     */
    //@ApiModelProperty("座机号")
    @Schema(description = "座机号，用户的座机号码")
    @TableField(value = "telephone")
    private String telephone;

    /**
     * 地址，用户的联系地址
     */
    //@ApiModelProperty("地址")
    @Schema(description = "地址，用户的联系地址")
    @TableField(value = "address")
    private String address;

    /**
     * 是否可用，true：启用，false：禁用
     */
    //@ApiModelProperty("是否禁用，true：启用，false：禁用")
    @Schema(description = "是否可用，true：启用，false：禁用")
    @TableField(value = "enabled")
    @Getter(AccessLevel.NONE) // 表示该字段不生成getter方法, 否则与 UserDetails中的getEnabled方法冲突
    private Boolean enabled;

    /**
     * 用户头像，存储头像图片的URL
     */
    //@ApiModelProperty("用户头像")
    @Schema(description = "用户头像，存储头像图片的URL")
    @TableField(value = "userFace")
    private String userFace;


    /**
     * 备注，用户的附加信息
     */
    //@ApiModelProperty("备注")
    @Schema(description = "备注，用户的附加信息")
    @TableField(value = "remark")
    private String remark;

    /**
     * 角色列表
     */
    //@ApiModelProperty("角色")
    @Schema(description = "角色列表")
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * 获取鉴权列表
     * 将用户的角色转换为权限列表
     * 这里假设角色存储在roles字段中，多个角色用逗号分隔
     * @return 权限集合
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 如果没有分配角色，默认返回空列表
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    /**
     * 判断 JWT token 权限是否没过期
     * @return  boolean
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 获取用户权限是否没被锁
     * @return boolean
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 获取用户密码是否没过期
     * @return  boolean
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 获取用户是否可用
     * @return boolean
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }
}