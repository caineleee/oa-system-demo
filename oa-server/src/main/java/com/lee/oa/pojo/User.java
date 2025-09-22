package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 用户实体类
 * 
 * @author Lee
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("users")
@ApiModel(value = "用户实体类")
public class User implements Serializable, UserDetails {
    /**
     * 用户ID，主键，自增
     */
    @ApiModelProperty("用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 手机
     */
    @ApiModelProperty("手机")
    private String phone;

    /**
     * 座机号
     */
    @ApiModelProperty("座机号")
    private String telephone;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;

    /**
     * 是否可用，true：启用，false：禁用
     */
    @ApiModelProperty("是否禁用，true：启用，false：禁用")
    private Boolean enabled;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String userface;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 获取鉴权列表
     * @return boolean
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(null);
    }

    /**
     * 判断 JWT token 权限是否没过期
     * @return  boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 获取用户权限是否没被锁
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 获取用户密码是否没过期
     * @return  boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 获取用户是否可用
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}