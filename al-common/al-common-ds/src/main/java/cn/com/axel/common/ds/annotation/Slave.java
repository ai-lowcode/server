package cn.com.axel.common.ds.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: axel
 * @description: 从库数据源配置注解
 * @date: 2021/11/30 11:54
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@DS("slave")
public @interface Slave {
}
