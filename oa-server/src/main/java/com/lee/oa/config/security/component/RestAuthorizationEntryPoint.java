package com.lee.oa.config.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.oa.pojo.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName RestAuthorizationEntryPoint
 * @Description 未登录或 token 失效时的处理类，返回自定义JSON格式结果
 * @Author lihongliang
 * @Date 2025/9/22 17:31
 * @Version 1.0
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 处理未认证的访问请求
     * 当用户未登录或token失效时调用此方法
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @param authException 认证异常
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 设置响应字符编码和内容类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // 获取响应输出流
        PrintWriter out = response.getWriter();

        // 创建未登录的错误响应
        Response resp = Response.error("未登录, 请先登录");
        // 设置HTTP状态码为401（UNAUTHORIZED）
        resp.setCode(HttpServletResponse.SC_UNAUTHORIZED);

        // 将响应对象转换为JSON格式并写入输出流
        out.write(new ObjectMapper().writeValueAsString(resp));
        // 刷新输出流确保数据发送
        out.flush();
        // 关闭输出流释放资源
        out.close();
    }
}