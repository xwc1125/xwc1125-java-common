package com.xwc1125.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 缓存注解
 * 注解可以用在方法或类上，但是缓存注解一般是使用在方法上的。
 * @Author: xwc1125
 * @Date: 2021/2/3 14:47
 * @Copyright Copyright@2021
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomCache {

    /**
     * 缓存的key值
     */
    String key();
}
