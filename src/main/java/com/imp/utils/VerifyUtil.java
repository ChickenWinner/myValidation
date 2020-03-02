package com.imp.utils;

import com.imp.annotations.*;

import java.lang.annotation.Annotation;
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
     * 取出对象中的属性，获得每个属性上的注解
     * 调用方法 检验每个属性
     *
     * @param parameter 参数对象
     * @param object  参数值
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
            // 一个属性一个属性的检验
            msg = getAnnotations(field, field.get(object));
            // 添加检验信息 如果成功msg为null
            if(msg != null)
            sb.append(msg).append(";");
        }
        // 如果有出错信息
        if (sb.length() > 0) {
            // 删除最后一个逗号
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 检验基本类型
     *
     * @param parameter 参数对象
     * @param object 参数值
     * @return 检验信息
     */
    public static String normalVerify(Parameter parameter, Object object) {
        // 参数对象，参数值
        if(getAnnotations(parameter, object) != null )
        return getAnnotations(parameter, object).trim();
        return "";
    }


    /**
     * 尝试取出每个属性或者参数前的所有注解
     *
     * @param parameter 参数对象
     * @param object    参数值
     * @return 检验信息
     */
    private static String getAnnotations(Object parameter, Object object) {
        Annotation[] annotations;
        // 如果是属性
        if (parameter.getClass().equals(Field.class)) {
            // 获得所有注解
            annotations = ((Field) parameter).getAnnotations();
            return doMethod(parameter, annotations, object);
        } else { // 否则就是方法的参数
            annotations = ((Parameter) parameter).getAnnotations();
            return doMethod(parameter, annotations, object);
        }
    }


    /**
     * 判断每个是哪个注解，去执行对应的方法
     * @param annotations 注解数组
     * @param object 值
     * @return 检验信息
     */
    private static String doMethod(Object parameter, Annotation[] annotations, Object object) {
        // 检验信息
        StringBuilder sb = new StringBuilder();
        // 如果注解数组不空
        if (annotations != null && annotations.length > 0) {
            // 遍历所有注解
            for (Annotation annotation : annotations) {
                // 判断是哪个注解，调用相应的方法
                if (annotation instanceof IsEmail) {
                    // 如果有错误信息，进行拼接
                    if(VerifyEmail((IsEmail) annotation, object) != null) {
                        sb.append(VerifyEmail((IsEmail) annotation, object)).append(",");
                    }
                } else if(annotation instanceof NotEmpty) {
                    if(VerifyEmpty(parameter,(NotEmpty)annotation, object) != null) {
                        sb.append(VerifyEmpty(parameter,(NotEmpty) annotation, object)).append(",");
                    }
                } else if(annotation instanceof Length) {
                    if(VerifyLength(parameter,(Length)annotation, object) != null) {
                        sb.append(VerifyLength(parameter,(Length) annotation, object)).append(",");
                    }
                } else if(annotation instanceof Max) {
                    if(VerifyMax(parameter,(Max)annotation, object) != null) {
                        sb.append(VerifyMax(parameter,(Max) annotation, object)).append(",");
                    }
                } else if(annotation instanceof Min) {
                    if(VerifyMin(parameter,(Min)annotation, object) != null) {
                        sb.append(VerifyMin(parameter,(Min) annotation, object)).append(",");
                    }
                }
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString().trim();
            }
        }
        // 都不是或者没有注解，返回Null
        return null;
    }


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
            return matcher.matches() ? null : annotation.keyName() + "," + annotation.msg();
        }
        // 没有注解，则直接检验通过
        return null;
    }

    private static String VerifyEmpty(Object parameter, NotEmpty annotation, Object object) {
        String name = "";
        // 获得参数名
//        if(parameter.getClass().equals(Field.class)) {
//            name = ((Field)parameter).getName();
//        }


        // 如果有注解且值不为空
        if(annotation != null && object != null && object.toString().length() > 0) {
            // 如果有手动写value值，且不满足该value值
            if(!annotation.value().equals("")
                    && !annotation.value().equals(object.toString())) {
                return annotation.keyName() + "," + name +"必须等于" + annotation.value();
            }
            return null;
        }
        // 如果有注解且值为空
        if(annotation != null && (object == null || object.toString().length() == 0)) {
            return annotation.keyName() + ","  + annotation.msg();
        }
        return null;
    }

    // 检验长度
    private static String VerifyLength(Object parameter, Length annotation, Object object) {

        String name = "";
        // 获得参数名
        if(parameter.getClass().equals(Field.class)) {
            name = ((Field)parameter).getName();
        }
        // 如果有注解，允许为空， 值为空
        if(annotation != null && annotation.isNull() && (object == null ||
        object.toString().length() == 0) ) {
            // 没有设置最小长度
            if(annotation.min() == 0) {
                return null;
            } else {
                return annotation.keyName() + "," + annotation.msg();
            }
        }
        // 如果有注解，不允许为空， 值为空 不通过
        if(annotation != null && !annotation.isNull() && (object == null ||
                object.toString().length() == 0) ) {
            return annotation.keyName() + name +"长度不能为空";
        }
        // 如果有注解，值不为空
        if(annotation != null && object != null && object.toString().length() > 0) {
            // 如果长度不在范围内
            if(object.toString().length() > annotation.max() ||
                        object.toString().length() < annotation.min())
            return annotation.keyName() + "," + annotation.msg();
        }

        return null;
    }

    // 最大数值
    private static String VerifyMax(Object parameter, Max annotation, Object object) {

        // 如果有注解，允许为空， 值为空
        if(annotation != null && annotation.isNull() && (object == null ||
                object.toString().length() == 0) ) {
            return null;
        }
        // 如果有注解，不允许为空， 值为空 不通过
        if(annotation != null && !annotation.isNull() && (object == null ||
                object.toString().length() == 0) ) {
            return annotation.keyName() +"值不能为空";
        }
        // 如果有注解，值不为空
        if(annotation != null && object != null && object.toString().length() > 0) {
            // 如果数值不在范围内
            if(Integer.parseInt(object.toString()) > annotation.value())
                return annotation.keyName() + "," + annotation.msg();
        }
        return null;
    }

    // 最小数值
    private static String VerifyMin(Object parameter, Min annotation, Object object) {


        // 如果有注解，允许为空， 值为空
        if(annotation != null && annotation.isNull() && (object == null ||
                object.toString().length() == 0) ) {
            return null;
        }
        // 如果有注解，不允许为空， 值为空 不通过
        if(annotation != null && !annotation.isNull() && (object == null ||
                object.toString().length() == 0) ) {
            return annotation.keyName() +"值不能为空";
        }
        // 如果有注解，值不为空
        if(annotation != null && object != null && object.toString().length() > 0) {
            // 如果数值不在范围内
            if(Integer.parseInt(object.toString()) < annotation.value())
                return annotation.keyName() + "," + annotation.msg();
        }
        return null;
    }

}
