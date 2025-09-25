package com.lee.oa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类
 * 用于配置Swagger API文档生成的相关设置
 * 解决Springfox与Spring Boot 2.7的兼容性问题
 */
@Configuration
// 启用Swagger2文档生成功能
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * 配置Swagger Docket实例
     * 创建并配置Docket对象，用于生成API文档
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
                // 指定扫描的包路径，只扫描controller包下的接口
                .apis(RequestHandlerSelectors.basePackage("com.lee.oa.controller"))
                // 对所有路径进行监控
                .paths(PathSelectors.any())
                // 构建Docket对象
                .build()
                // 配置安全上下文
                .securityContexts(securityContexts())
                // 配置安全方案
                .securitySchemes(securitySchemes());
    }

    /**
     * 配置API基本信息
     * 设置API文档的标题、描述、版本、联系人等信息
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
     * 用于处理Swagger UI相关的静态资源，确保Swagger UI页面能够正确加载
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
                
        // 添加Swagger资源配置，处理swagger-resources相关请求
        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");

        // 处理API文档的请求路径
        registry.addResourceHandler("/v2/api-docs")
                .addResourceLocations("classpath:/META-INF/resources/");

        // 处理Swagger UI配置请求
        registry.addResourceHandler("/swagger-resources/configuration/ui")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/configuration/ui/");

        // 处理Swagger安全配置请求
        registry.addResourceHandler("/swagger-resources/configuration/security")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/configuration/security/");
    }

    /**
     * 配置Swagger的安全方案, 设置请求头
     * 定义API调用时的安全验证方式，这里使用ApiKey方式通过Authorization头传递JWT Token
     *
     * @return 包含ApiKey的安全方案列表
     */
    private List<SecurityScheme> securitySchemes() {
        // 创建安全方案列表
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        // 添加ApiKey安全方案，名称为Authorization，参数名为Authorization，位置在请求头中
        securitySchemes.add(new ApiKey("Authorization", "Authorization", "header"));
        // 返回安全方案列表
        return securitySchemes;
    }

    /**
     * 配置Swagger的安全上下文路径, 设置需要登录认证的路径
     * 定义哪些API路径需要进行安全验证
     *
     * @return 包含需要应用安全策略的路径列表
     */
    private List<SecurityContext> securityContexts() {
        // 创建安全上下文列表
        return new ArrayList<SecurityContext>() {{
            // 添加安全上下文配置
            add(SecurityContext.builder()
                    // 设置安全引用
                    .securityReferences(defaultAuth())
                    // 设置操作选择器，true表示对所有操作应用安全验证
                    .operationSelector(operationContext -> true)
                    // 构建安全上下文对象
                    .build());
        }};
    }

    /**
     * 配置默认的安全引用，用于Swagger UI中的API调用认证
     * 定义安全验证的范围和引用方式
     *
     * @return 包含安全引用的列表，用于API调用时的认证信息
     */
    private List<SecurityReference> defaultAuth() {
        // 创建全局访问授权范围
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        // 创建授权范围数组
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        // 设置数组第一个元素为全局访问范围
        authorizationScopes[0] = authorizationScope;
        // 返回安全引用列表
        return new ArrayList<SecurityReference>() {{
            // 添加安全引用，名称为Authorization，使用之前定义的授权范围
            add(new SecurityReference("Authorization", authorizationScopes));
        }};
    }
}