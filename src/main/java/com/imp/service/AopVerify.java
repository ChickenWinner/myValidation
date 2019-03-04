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
public class AopVerify {

    // 切点为controller包下的所有类的所有方法
    @Pointcut("execution(* com.imp.controller.*.*(..))")
    public void doVerify(){}

    /**
     * 在每个需要被检验的方法前执行
     * @param joinPoint
     * @throws Exception
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
        Object[] args = joinPoint.getArgs();
        // 获得方法的所有参数的对象
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        // 记录检验结果信息
        StringBuilder sb = new StringBuilder();
        // 遍历所有参数
        for(int i = 0; i < parameters.length; i++) {
            // TODO：dto名称先写定了
            // 如果是dto对象
            // args的getName带包名?
            if(args[i].getClass().getName().contains("com.imp.dto")) {
                // 尝试获取对象前的ObjVerify注解
                ObjVerify objVerify = parameters[i].getAnnotation(ObjVerify.class);
                // 如果有ObjVerify注解，检验该对象
                if (objVerify != null) {
                    sb.append(VerifyUtil.objVerify(parameters[i], args[i]));
                }
            } else {
                // 如果是基本类型，
                sb.append(VerifyUtil.normalVerify(parameters[i], args[i]));
            }

        }
        if(sb.length() > 1){
            // 异常码 异常信息
            throw new VerifyException(VerifyMsg.NOT_PASS_VERIFI.getCode(),sb.toString());
        }
    }
}
