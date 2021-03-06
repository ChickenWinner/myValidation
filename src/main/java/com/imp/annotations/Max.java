package com.imp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数字类型最大值
 * 可以作为对象的属性，可以作为方法的参数
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
public @interface Max {

    // 是否允许为空
    boolean isNull() default true;

    // 默认最大值
    int value() default Integer.MAX_VALUE;

    // 对应字段名字
    String keyName()default "注意";

    String msg() default "不能超过最大值";
}