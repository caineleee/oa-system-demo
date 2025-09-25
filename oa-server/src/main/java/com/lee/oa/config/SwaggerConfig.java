package com.lee.oa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 * 用于配置Swagger API文档生成的相关设置
 * 解决Springfox与Spring Boot 2.7的兼容性问题
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * 配置Swagger Docket实例
     * 
     * @return Docket Swagger配置对象
     */
    @Bean
    public Docket api() {
        // 创建Docket对象，指定使用Swagger2文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                // 设置API基本信息
                .apiInfo(apiInfo())
                // 选择哪些接口作为API展示
                .select()
                // 指定扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.lee.oa.controller"))
                // 对所有路径进行监控
                .paths(PathSelectors.any())
                // 构建Docket对象
                .build();
    }

    /**
     * 配置API基本信息
     * 
     * @return ApiInfo API基本信息对象
     */
    private ApiInfo apiInfo() {
        // 创建ApiInfoBuilder对象用于构建API信息
        return new ApiInfoBuilder()
                // 设置文档标题
                .title("OA System API")
                // 设置文档描述
                .description("OA System API Documentation")
                // 设置文档版本
                .version("1.0")
                // 设置联系人信息
                .contact(new Contact("Lee", "http://localhost:8081", "lee@example.com"))
                // 构建ApiInfo对象
                .build();
    }
    
    /**
     * 添加静态资源处理器
     * 用于处理Swagger UI相关的静态资源
     *
     * @param registry 资源处理器注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将swagger-ui.html映射到classpath下的META-INF/resources目录
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        // 将/webjars/**映射到classpath下的META-INF/resources/webjars目录
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
                
        // 添加Swagger资源配置
        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");

        registry.addResourceHandler("/v2/api-docs")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/swagger-resources/configuration/ui")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/configuration/ui/");

        registry.addResourceHandler("/swagger-resources/configuration/security")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/configuration/security/");
    }
}