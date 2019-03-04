package com.imp.service;

import com.imp.annotations.NeedVerify;
import com.imp.annotations.ObjVerify;
import com.imp.enums.VerifyMsg;
import com.imp.exception.VerifyException;
import com.imp.utils.VerifyUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

/**
 * Aop: 切点为controller下的所有方法
 *
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/3 20:51
 */

@Aspect
@Component
@PropertySource({"classpath:config/setting.properties"})
public class AopVerify {

    @Value("${base.location}")
    private String BASE_LOCATION; // 从配置文件中读取dto所在包

    // 切点为controller包下的所有类的所有方法
    @Pointcut("execution(* com.imp.controller.*.*(..))")
    public void doVerify(){}

    /**
     * 在每个需要被检验的方法前执行
     * @param joinPoint 切点参数
     * @throws Exception 抛出的异常
     */
    @Before("doVerify()")
    public void paramVerify(JoinPoint joinPoint) throws Exception {

        // 获得方法签名
        MethodSignature methodSignature  = (MethodSignature) joinPoint.getSignature();
        // 尝试获得方法上的NeedVerify注解
        NeedVerify needVerify = methodSignature.getMethod().getAnnotation(NeedVerify.class);
        // 没有NeedVerify注解，不做处理
        if(needVerify == null) {
            return;
        }
        // 获得方法的所有参数
        // args的好处是可以获得参数的值
        Object[] args = joinPoint.getArgs();
        // 获得方法的所有参数的对象
        // parameter的好处是可以获得参数前的的注解
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        // 记录检验结果信息
        StringBuilder sb = new StringBuilder();
        // 遍历所有参数
        for(int i = 0; i < parameters.length; i++) {
            // 如果是参数是dto对象，判断依据是类名含有dto的包名
            // 2个不同对象getName()区别
            // args:com.imp.dto.Person
            // param:[Ljava.lang.reflect.Parameter;
            // TODO: 修改判断对象的方式，如果不是基本类型，就是对象
            if(args[i] != null && args[i].getClass().getName().contains(BASE_LOCATION)) {
                // 尝试获取参数为对象前的ObjVerify注解
                ObjVerify objVerify = parameters[i].getAnnotation(ObjVerify.class);
                // 如果有ObjVerify注解，检验该对象
                if (objVerify != null) {
                    sb.append(VerifyUtil.objVerify(parameters[i], args[i]));
                }
            } else {
                // 如果是基本类型
                sb.append(VerifyUtil.normalVerify(parameters[i], args[i]));
            }

        }
        // 如果有检验出错的信息，抛出异常
        // 抛出的异常会被全局异常捕获器捕获
        if(sb.length() > 1){
            // 异常码 异常信息
            throw new VerifyException(VerifyMsg.NOT_PASS_VERIFI.getCode(),sb.toString());
        }
    }
}
