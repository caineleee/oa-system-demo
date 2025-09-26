package com.lee.oa.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @ClassName CaptchaConfig
 * @Description Captcha 验证码配置类
 * @Author lihongliang
 * @Date 2025/9/26 08:52
 * @Version 1.0
 */
@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        // 验证码生成器
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        // 配置
        Properties properties = new Properties();
        // 边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "40");
        // 验证码间距
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 验证码图片宽度
        properties.setProperty("kaptcha.image.width", "100");
        // 验证码图片高度
        properties.setProperty("kaptcha.image.height", "40");
        // 验证码实现
        defaultKaptcha.setConfig(new Config(properties));

        return defaultKaptcha;
    }


}
