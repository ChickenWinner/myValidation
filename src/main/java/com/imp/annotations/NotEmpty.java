package com.imp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不能为空的注解
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/4 21:03
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface NotEmpty {

    // 默认是没有值 如有值 就说明该字段必须为该值！！！！！
    String value() default "";
    // 对应字段名字
    String keyName() default "注意";
    // 信息
    String msg() default "字段不能为空";
}
