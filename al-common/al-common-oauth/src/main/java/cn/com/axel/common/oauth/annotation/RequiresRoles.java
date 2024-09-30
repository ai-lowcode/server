package cn.com.axel.common.oauth.annotation;

import cn.com.axel.common.oauth.common.Logical;

import java.lang.annotation.*;

/**
 * @author: axel
 * @description: 角色校验注解
 * @date: 2022/12/5 17:51
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresRoles {
    String[] value();

    Logical logical() default Logical.OR;
}