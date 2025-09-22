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
 * @Description 访问接口没有权限, 返回自定义结果
 * @Author lihongliang
 * @Date 2025/9/22 17:44
 * @Version 1.0
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        Response resp = Response.error("权限不足, 请联系管理员");
        resp.setCode(HttpServletResponse.SC_FORBIDDEN);

        out.write(new ObjectMapper().writeValueAsString(resp));
        out.flush();
        out.close();
    }
}
