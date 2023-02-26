package com.xwc1125.common.util.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-04-30 22:31
 * @Copyright Copyright@2019
 */
public class AnnotationUtils extends org.springframework.core.annotation.AnnotationUtils {
    public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
        A annotation = org.springframework.core.annotation.AnnotationUtils.findAnnotation(method, annotationType);
        return annotation;
    }

    public static <A extends Annotation> A getAnnotation(Class clz, Class<A> annotationType) {
        A annotation = org.springframework.core.annotation.AnnotationUtils.findAnnotation(clz, annotationType);
        return annotation;
    }

    /**
     * 是否存在
     *
     * @param method
     * @param annotationType
     * @return
     */
    public static boolean isAnnotationPresent(Method method, Class<? extends Annotation> annotationType) {
        Annotation annotation = org.springframework.core.annotation.AnnotationUtils.findAnnotation(method, annotationType);
        if (annotation == null) {
            return false;
        }
        return true;
    }

    public static boolean isAnnotationPresent(Class clz, Class<? extends Annotation> annotationType) {
        Annotation annotation = org.springframework.core.annotation.AnnotationUtils.findAnnotation(clz, annotationType);
        if (annotation == null) {
            return false;
        }
        return true;
    }
}
