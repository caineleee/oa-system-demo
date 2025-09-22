//package com.lee.oa.controller;
//
//import com.lee.oa.pojo.AuthenticationRequest;
//import com.lee.oa.pojo.Response;
//import com.lee.oa.config.security.JwtUtil;
//import com.lee.oa.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 认证控制器，用于处理用户认证相关请求
// *
// * @author Lee
// * @since 1.0.0
// */
//@RestController
//@CrossOrigin
//public class AuthenticationController {
//
//    /**
//     * 认证管理器
//     */
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    /**
//     * 用户详情服务实现类
//     */
//    @Autowired
//    private UserService userService;
//
//    /**
//     * JWT工具类
//     */
//    @Autowired
//    private JwtUtil jwtTokenUtil;
//
//    /**
//     * 创建认证令牌
//     *
//     * @param authenticationRequest 认证请求对象
//     * @return ResponseEntity 响应实体
//     * @throws Exception 异常
//     */
//    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//        try {
//            // 执行用户认证
//            authenticationManager.authenticate(
//                    // 创建用户名密码认证令牌
//                    new UsernamePasswordAuthenticationToken(
//                            // 设置用户名
//                            authenticationRequest.getUsername(),
//                            // 设置密码
//                            authenticationRequest.getPassword())
//            );
//        } catch (DisabledException e) {
//            // 用户被禁用时抛出异常
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            // 凭证错误时抛出异常
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//
//        // 根据用户名加载用户详情
//        UserDetails userDetails = userService.findByUsername(authenticationRequest.getUsername());
//
//        // 生成JWT令牌
//        String jwt = jwtTokenUtil.generateToken(userDetails);
//
//        // 返回认证响应
//        return ResponseEntity.ok(Response.success("认证成功", jwt));
//    }
//}