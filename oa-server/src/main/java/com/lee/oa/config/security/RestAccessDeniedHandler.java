package com.lee.oa.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.oa.pojo.Response;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName RestAccessDeniedHandler
 * @Description 访问接口没有权限时的处理类，返回自定义JSON格式结果
 * @Author lihongliang
 * @Date 2025/9/22 17:44
 * @Version 1.0
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 处理访问被拒绝的请求
     * 当用户已认证但没有足够权限访问某个资源时调用此方法
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @param accessDeniedException 访问被拒绝异常
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置响应字符编码和内容类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // 获取响应输出流
        PrintWriter out = response.getWriter();
        // 创建权限不足的错误响应
        Response resp = Response.error("权限不足, 请联系管理员");
        // 设置HTTP状态码为403（FORBIDDEN）
        resp.setCode(HttpServletResponse.SC_FORBIDDEN);

        // 将响应对象转换为JSON格式并写入输出流
        out.write(new ObjectMapper().writeValueAsString(resp));
        // 刷新输出流确保数据发送
        out.flush();
        // 关闭输出流释放资源
        out.close();
    }
}