package cn.com.axel.common.core.annotation;

import cn.com.axel.common.core.advice.ExceptionHandlerAdvice;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: axel
 * @description: 全局异常处理注解
 * @date: 2021/12/13 18:32
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ExceptionHandlerAdvice.class})
public @interface GlobalException {
    String value() default "";
}
