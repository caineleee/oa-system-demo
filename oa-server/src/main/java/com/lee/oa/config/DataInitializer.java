package com.lee.oa.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.oa.pojo.User;
import com.lee.oa.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataAccessException;

/**
 * 数据初始化器，在应用启动时初始化基础数据
 * 
 * @author Lee
 * @since 1.0.0
 */
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    /**
//     * 用户Mapper，用于数据库操作
//     */
//    @Autowired
//    private UserMapper userMapper;
//
//    /**
//     * 在应用启动后执行数据初始化
//     *
//     * @param args 命令行参数
//     * @throws Exception 异常
//     */
//    @Override
//    public void run(String... args) throws Exception {
//        try {
//            // 打印初始化开始日志
//            System.out.println("开始初始化数据...");
//
//            // 检查是否已存在用户，如果不存在则创建一个测试用户
//            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//            // 设置查询条件：用户名等于admin
//            queryWrapper.eq("username", "admin");
//
//            long count = 0;
//            try {
//                // 查询符合条件的用户数量
//                count = userMapper.selectCount(queryWrapper);
//                // 打印查询结果日志
//                System.out.println("查询到admin用户数量: " + count);
//            } catch (DataAccessException e) {
//                // 处理数据库访问异常
//                System.err.println("查询用户表时出错，可能是表不存在: " + e.getMessage());
//                System.err.println("请确保数据库和表已正确创建");
//                return;
//            }
//
//            // 检查是否需要创建测试用户
//            if (count == 0) {
//                // 创建新的用户对象
//                User admin = new User();
//                // 设置用户名
//                admin.setUsername("admin");
//                // 创建密码编码器
//                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//                // 设置密码（经过编码）
//                admin.setPassword(passwordEncoder.encode("admin"));
//
//                // 打印插入前日志
//                System.out.println("准备插入用户: " + admin.getUsername());
//                // 插入用户到数据库
//                userMapper.insert(admin);
//                // 打印插入成功日志
//                System.out.println("创建测试用户: admin/admin");
//            } else {
//                // 打印用户已存在日志
//                System.out.println("admin用户已存在，跳过初始化");
//            }
//        } catch (Exception e) {
//            // 处理其他异常
//            System.err.println("数据初始化过程中出现错误: " + e.getMessage());
//            // 打印异常堆栈
//            e.printStackTrace();
//        }
//    }
//}