package com.xwc1125.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 方法、类注解
 * @Author: xwc1125
 * @Date: 2021/2/3 14:37
 * @Copyright Copyright@2021
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCheck {

    /**
     * 资源key
     */
    String resourceKey();
}
