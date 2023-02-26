package com.xwc1125.common.annotation;

import java.lang.annotation.*;

/**
 * Description: 数据权限过滤注解
 * </p>
 *
 * @Author: xwc1125
 * @Date: 2019-03-23 19:07:02
 * @Copyright Copyright@2019
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    /**
     * 表的别名
     */
    String tableAlias() default "";
}
