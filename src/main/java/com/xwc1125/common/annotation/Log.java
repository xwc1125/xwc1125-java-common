package com.xwc1125.common.annotation;

import com.xwc1125.common.constant.BusinessType;
import com.xwc1125.common.constant.OperatorType;

import java.lang.annotation.*;

/**
 * Description: 自定义操作日志记录注解
 * </p>
 *
 * @Author: xwc1125
 * @Date: 2019-03-23 19:08:03
 * @Copyright Copyright@2019
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
