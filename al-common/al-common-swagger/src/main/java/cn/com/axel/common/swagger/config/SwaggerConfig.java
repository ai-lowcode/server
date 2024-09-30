package cn.com.axel.common.swagger.config;

import cn.com.axel.common.core.constants.Constants;
import cn.com.axel.common.core.utils.StringUtils;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: axel
 * @description: swagger配置类
 * @date: 2021/11/15 12:59
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig implements WebMvcConfigurer {
    /**
     * 配置Swagger接口文档的详细信息
     *
     * @param swaggerProperties 包含Swagger相关配置的属性对象
     * @return 返回配置好的OpenAPI对象
     */
    @Bean
    public OpenAPI openAPIBean(SwaggerProperties swaggerProperties) {
        OpenAPI api = new OpenAPI().info(apiInfo(swaggerProperties))
                .externalDocs(new ExternalDocumentation()
                        .description(swaggerProperties.getDescription())
                        .url(swaggerProperties.getTermsOfServiceUrl()))
                .addSecurityItem(new SecurityRequirement().addList(Constants.AUTHENTICATION))
                .components(new Components().addSecuritySchemes(Constants.AUTHENTICATION
                        , new SecurityScheme().name(Constants.AUTHENTICATION)
                                .in(SecurityScheme.In.HEADER).type(SecurityScheme.Type.HTTP).scheme("Bearer")));
        if (!StringUtils.isEmpty(swaggerProperties.getPrefix())) {
            api.addServersItem(new Server().url(swaggerProperties.getPrefix()));
        }
        return api;
    }

    /**
     * 接口信息
     *
     * @param swaggerProperties swagger属性
     * @return 返回接口信息
     */
    private Info apiInfo(SwaggerProperties swaggerProperties) {
        return new Info()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(new License().name(swaggerProperties.getLicense()).url(swaggerProperties.getLicenseUrl()))
                .contact(swaggerProperties.getContact().getContact())
                .termsOfService(swaggerProperties.getTermsOfServiceUrl())
                .version(swaggerProperties.getVersion());
    }

}
