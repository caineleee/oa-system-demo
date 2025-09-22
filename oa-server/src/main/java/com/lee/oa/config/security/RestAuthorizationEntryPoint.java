package com.lee.oa.config.security;

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
 * @Description 未登录或 token 失效, 返回自定义结果
 * @Author lihongliang
 * @Date 2025/9/22 17:31
 * @Version 1.0
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {


    /**
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();


        Response  resp = Response.error("未登录, 请先登录");
        resp.setCode(HttpServletResponse.SC_UNAUTHORIZED);

        out.write(new ObjectMapper().writeValueAsString(resp));
        out.flush();
        out.close();


    }
}
