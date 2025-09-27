package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName LoginInfo
 * @Description 用户登录信息实体类，用于接收前端传递的登录参数
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
@TableName("t_user")
public class LoginInfo {

    /**
     * 用户名，用于用户身份识别
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    
    /**
     * 密码，用于用户身份验证
     */
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    
    /**
     * 验证码，用于防止机器人登录
     */
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}