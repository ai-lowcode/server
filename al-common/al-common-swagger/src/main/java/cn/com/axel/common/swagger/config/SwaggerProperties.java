package cn.com.axel.common.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author: axel
 * @description: swagger基本属性
 * @date: 2021/11/22 17:22
 */
@Configuration
@ConfigurationProperties(prefix = "swagger")
@Data
@RefreshScope
public class SwaggerProperties {
    /**
     * 是否开启swagger
     */
    private Boolean enabled = true;
    private String title = "Axel框架";
    private String description = "Axel框架接口文档";
    private String termsOfServiceUrl = "";
    private String license = "";
    private String licenseUrl = "";
    private String version = "版本号:V1.3.1";
    private MyContact contact;
    //请求地址前缀
    private String prefix;
}
