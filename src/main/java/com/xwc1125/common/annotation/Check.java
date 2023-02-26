package com.xwc1125.common.annotation;

import com.xwc1125.common.annotation.impl.ParamConstraintValidated;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 字段注解
 * 字段注解一般是用于校验字段是否满足要求，hibernate-validate依赖就提供了很多校验注解 ，
 * 如@NotNull、@Range等，但是这些注解并不是能够满足所有业务场景的。
 * </br>
 * @Target 定义注解的使用位置，用来说明该注解可以被声明在那些元素之前。
 * ElementType.TYPE：说明该注解只能被声明在一个类前。
 * ElementType.FIELD：说明该注解只能被声明在一个类的字段前。
 * ElementType.METHOD：说明该注解只能被声明在一个类的方法前。
 * ElementType.PARAMETER：说明该注解只能被声明在一个方法参数前。
 * ElementType.CONSTRUCTOR：说明该注解只能声明在一个类的构造方法前。
 * ElementType.LOCAL_VARIABLE：说明该注解只能声明在一个局部变量前。
 * ElementType.ANNOTATION_TYPE：说明该注解只能声明在一个注解类型前。
 * ElementType.PACKAGE：说明该注解只能声明在一个包名前
 * </br>
 * @Constraint 通过使用validatedBy来指定与注解关联的验证器
 * </br>
 * @Retention 用来说明该注解类的生命周期。
 * RetentionPolicy.SOURCE: 注解只保留在源文件中
 * RetentionPolicy.CLASS : 注解保留在class文件中，在加载到JVM虚拟机时丢弃
 * RetentionPolicy.RUNTIME: 注解保留在程序运行期间，此时可以通过反射获得定义在某个类上的所有注解。
 * </br>
 * @Author: xwc1125
 * @Date: 2021/2/3 11:35
 * @Copyright Copyright@2021
 */
@Target({ElementType.FIELD}) //只允许用在类的字段上
@Retention(RetentionPolicy.RUNTIME) //注解保留在程序运行期间，此时可以通过反射获得定义在某个类上的所有注解
@Constraint(validatedBy = ParamConstraintValidated.class)
public @interface Check {

    /**
     * 合法的参数值
     */
    String[] paramValues();

    /**
     * 提示信息
     */
    String message() default "参数不为指定值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
