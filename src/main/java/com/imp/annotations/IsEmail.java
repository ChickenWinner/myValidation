package com.imp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检验email格式是否正确
 * 可以作为对象的属性，可以作为方法的参数
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
public @interface IsEmail {

    // 是否允许为空
    boolean isNull() default true;

    // 邮箱的正则表达式，也可以手动输入
    String value () default "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    // 对应字段名字
    String keyName()default "注意";

    String msg() default "邮箱格式不正确";
}