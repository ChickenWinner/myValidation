package com.imp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检验String长度是否合法
 * 可以作为对象的属性，可以作为方法的参数
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
public @interface Length {

    String keyName()default "注意";

    // 是否允许为空
    boolean isNull () default true;

    // 长度默认是0
    int min () default 0;
    // 最大长度
    int max () default  Integer.MAX_VALUE;

    String msg() default "";

}