package cn.com.axel.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 服务属性
 * @author: axel
 * @date: 2024/1/31
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "axel")
public class ServiceProperties {
    private String version;
    private String type = "cloud";
}
