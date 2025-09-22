//package com.lee.oa.service.Impl;
//
//import com.lee.oa.mapper.UserMapper;
//import com.lee.oa.pojo.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
///**
// * 用户详情服务实现类，用于处理用户认证相关逻辑
// *
// * @author Lee
// * @since 1.0.0
// */
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    /**
//     * 用户Mapper，用于数据库操作
//     */
//    @Autowired
//    private UserMapper userMapper;
//
//    /**
//     * 根据用户名加载用户详情
//     *
//     * @param username 用户名
//     * @return UserDetails 用户详情对象
//     * @throws UsernameNotFoundException 当用户不存在时抛出此异常
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 根据用户名查找用户
//        Optional<User> user = userMapper.findByUsername(username);
//        // 检查用户是否存在
//        if (user.isEmpty()) {
//            // 用户不存在时抛出异常
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//
//        // 构建并返回Spring Security用户详情对象
//        return new org.springframework.security.core.userdetails.User(
//                // 设置用户名
//                user.get().getUsername(),
//                // 设置密码
//                user.get().getPassword(),
//                // 设置权限列表
//                new ArrayList<>()
//        );
//    }
//}