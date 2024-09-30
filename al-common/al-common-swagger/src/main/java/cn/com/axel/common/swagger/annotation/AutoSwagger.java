package cn.com.axel.common.swagger.annotation;

import cn.com.axel.common.swagger.config.SwaggerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: axel
 * @description: swagger自动配置注解
 * @date: 2021/11/15 13:12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SwaggerConfig.class)
public @interface AutoSwagger {
}
