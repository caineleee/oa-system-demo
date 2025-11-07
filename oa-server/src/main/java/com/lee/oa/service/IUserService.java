package com.lee.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.oa.pojo.Response;
import com.lee.oa.pojo.Role;
import com.lee.oa.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName IUserService
 * @Description 用户服务接口，定义用户相关业务操作方法
 * @Author lihongliang
 * @Date 2025/9/21 11:02
 * @Version 1.0
 */
public interface IUserService extends IService<User>, UserDetailsService {

    /**
     * 用户登录方法
     * 处理用户登录请求，验证用户身份并生成JWT Token
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param request  请求对象，用于获取session信息
     * @return HTTP 响应对应，包含登录结果和token信息
     */
    Response login(String username, String password, String code, HttpServletRequest request);

    /**
     * 根据用户名加载用户详细信息
     * @param username 用户名
     * @return 用户详细信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 根据用户 ID 获取用户角色列表
     * @param userId 用户 ID
     * @return
     */
    List<Role> getRoles(Integer userId);

    /**
     * 获取所有操作员
     * @param keywords 关键字
     * @return 操作员列表
     */
    List<User> getAdmins(String keywords);

    /**
     * 添加操作员角色
     * @param userId 用户 ID
     * @param roleIds 角色 ID 列表
     * @return 更新结果
     */
    String AddUserRoles(Integer userId, List<Integer> roleIds);
}