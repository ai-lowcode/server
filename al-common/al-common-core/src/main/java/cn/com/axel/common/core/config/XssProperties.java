package cn.com.axel.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: axel
 * @date: 2021/8/12 10:08
 */
@Configuration
@ConfigurationProperties(prefix = "security.xss")
@RefreshScope
@Data
public class XssProperties {
    /**
     * Xss开关
     */
    private Boolean enabled = true;

    /**
     * 排除路径
     */
    private List<String> excludeUrls = new ArrayList<>();
}
