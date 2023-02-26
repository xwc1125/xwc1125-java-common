package com.xwc1125.common.annotation;

import com.xwc1125.common.constant.DataSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 自定义多数据源切换注解
 * </p>
 *
 * @Author: xwc1125
 * @Date: 2019-03-23 19:07:17
 * @Copyright Copyright@2019
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    /**
     * 切换数据源名称
     */
    public DataSourceType value() default DataSourceType.MASTER;
}
