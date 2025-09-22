package com.lee.oa.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName LoginInfo
 * @Description 用户登录实体类
 * @Author lihongliang
 * @Date 2025/9/21 10:36
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户登录实体类" )
public class LoginInfo {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
