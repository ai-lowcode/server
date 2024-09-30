package cn.com.axel.common.cloud.annotation;

import cn.com.axel.common.web.annotation.AutoWeb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: web应用微服务自动注解
 * @author: axel
 * @date: 2024/8/27
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoFeignClients
@AutoWeb
public @interface AutoCloud {
}
