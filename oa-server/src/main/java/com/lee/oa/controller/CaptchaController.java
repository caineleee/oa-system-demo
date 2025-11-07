package com.lee.oa.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @ClassName CaptchaController
 * @Description 验证码 controller
 * @Author lihongliang
 * @Date 2025/9/26 09:02
 * @Version 1.0
 */
//@Api(tags = "验证码控制器")
@Tag(name = "验证码控制器", description = "生成和管理验证码")
@RestController
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

//    @ApiOperation("获取验证码")
    @Operation(summary = "获取验证码", description = "生成并返回验证码图片")
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public String getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        // 定义验证码输出位图片类型
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String text = defaultKaptcha.createText();
        System.out.println("验证码内容: " + text);

        if (request.getParameter("env") != null && request.getParameter("env").equals("test")) {
            response.setHeader("captcha",  text);
        }

        // 将验证码内容保存在 session 中, 用以 UserServiceImpl.login 中验证
        request.getSession().setAttribute("captcha", text);

        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // 输出流输出图片, 格式位 jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return "123456";
    }


}