package com.imp.utils;

import com.imp.annotations.IsEmail;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检验参数工具类
 *
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/3 21:38
 */
public class VerifyUtil {

    /**
     * 检验对象
     *
     * @param parameter 参数对象
     * @param object    arg
     * @return 检验信息
     */
    public static String objVerify(Parameter parameter, Object object) throws Exception {
        // 检验信息
        StringBuilder sb = new StringBuilder();
        // 获取对象中的属性
        Field[] declaredFields = parameter.getType().getDeclaredFields();
        String msg;
        // 遍历所有属性
        for (Field field : declaredFields) {
            // 将属性设置成可访问
            field.setAccessible(true);
            // 属性对象，属性值
            msg = verifyTool(field, field.get(object));
            // 添加检验信息 如果成功msg为null
            sb.append(msg).append(",");
        }
        if (sb.length() > 0) {
            // 删除最后一个,
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 检验基本类型
     *
     * @param parameter
     * @param object
     * @return 检验信息
     */
    public static String normalVerify(Parameter parameter, Object object) {
        // 参数对象，参数值
        return verifyTool(parameter, object).trim();
    }

    /**
     * 执行检验
     *
     * @param parameter 参数对象
     * @param object    arg 通过arg取值
     * @return 检验信息
     */
    private static String verifyTool(Object parameter, Object object) {
        StringBuilder sb = new StringBuilder();
        String isEmail;

        isEmail = isEmail(parameter, object);
        sb.append(isEmail == null ? "" : isEmail + ",");
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString().trim();

    }

    private static String isEmail(Object parameter, Object object) {
        IsEmail annotation;
        if (parameter.getClass().equals(Field.class)) {
            // 获取属性上的IsEmail注解
            annotation = ((Field) parameter).getAnnotation(IsEmail.class);
        } else {
            // 获取参数前的IsEmail注解
            annotation = ((Parameter) parameter).getAnnotation(IsEmail.class);
        }
        return VerifyEmail(annotation, object);
    }

//    private static String isEmail(Field field, Object object) {
//        // 获取属性上的注解
//        IsEmail annotation = field.getAnnotation(IsEmail.class);
//        return VerifyEmail(annotation, object);
//    }


    /**
     * 检验Email格式
     *
     * @param annotation 注解
     * @param object     属性值
     * @return 检验信息
     */
    private static String VerifyEmail(IsEmail annotation, Object object) {
        // 有注解且允许为空，值为空，通过检验
        if (annotation != null && annotation.isNull()
                && (object == null || object.toString().length() == 0)) {
            return null;
        }
        // 有注解且不许为空，值是空，不通过
        if (annotation != null && !annotation.isNull()
                && (object == null || object.toString().length() == 0)) {
            return annotation.keyName() + "," + "该邮箱不可为空";
        }
        // 有注解且允许为空，但是不空  检验邮箱格式
        if (annotation != null && object != null
                && object.toString().length() > 0 && !annotation.value().equals("")) {
            Pattern compile = Pattern.compile(annotation.value()); // 正则
            Matcher matcher = compile.matcher(object.toString()); // 属性值是否匹配正则
            return matcher.matches() ? null : annotation.keyName() + "," + "请输入正确邮箱的格式";
        }
        // 没有注解，则直接检验通过
        return null;
    }
}
