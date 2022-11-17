package com.google.personalhealthrecordingplateform.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableKnife4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.google.personalhealthrecordingplateform.controllers"))
                .paths(PathSelectors.any())
                .build()
                //默认swagger授权有权限测试接口
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("健康管理平台接口文档")
                .description("健康管理平台接口文档")
                .contact(new Contact("bing", "http://localhost:8000/doc.html", "3120465015@qq.com"))
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        //设置请求头信息
        List<ApiKey> apiKeys = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        apiKeys.add(apiKey);
        return apiKeys;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> list = new ArrayList<>();
        list.add(getContextByPath("/hello/.*"));
        return list;
    }

    /**
     * 得到授权路径
     *
     * @param pathRegex
     * @return
     */
    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    /**
     * 默认swagger授权
     *
     * @return
     */
    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> list = new ArrayList<>();
        //授权范围和授权描述
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = scope;
        list.add(new SecurityReference("Authorization", authorizationScopes));
        return list;
    }
}
