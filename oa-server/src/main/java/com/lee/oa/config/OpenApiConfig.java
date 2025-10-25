package com.lee.oa.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI配置类
 * 用于配置SpringDoc OpenAPI文档生成的相关设置
 */
@Configuration
public class OpenApiConfig {

    /**
     * 配置OpenAPI实例
     * 创建并配置OpenAPI对象，用于生成API文档
     *
     * @return OpenAPI OpenAPI配置对象
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // 定义安全方案名称
        final String securitySchemeName = "Authorization";
        
        return new OpenAPI()
                // 设置API基本信息
                .info(new Info()
                        .title("OA System API")
                        .description("OA System API Documentation")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Lee")
                                .url("http://localhost:8081")
                                .email("lee@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                // 配置服务器信息
                .servers(List.of(
                        new Server()
                                .url("/")
                                .description("Default Server URL")))
                // 添加安全项
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // 配置组件
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .description("JWT Authorization header using the Bearer scheme")));
    }
    
    /**
     * 配置分组API文档，只扫描指定包下的Controller
     * @return GroupedOpenApi 分组API配置
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("oa-system")
                .packagesToScan("com.lee.oa.controller")
                .build();
    }
}