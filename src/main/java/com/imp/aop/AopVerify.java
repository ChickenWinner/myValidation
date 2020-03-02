package com.imp.aop;

import com.imp.annotations.NeedVerify;
import com.imp.enums.VerifyMsg;
import com.imp.exception.VerifyException;
import com.imp.utils.VerifyUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
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
public class AopVerify {


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
        // 获得方法的所有参数值
        Object[] args = joinPoint.getArgs();
        // 获得方法的所有参数的对象
        // parameter的好处是可以获得参数前的的注解
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        // 记录检验结果信息
        StringBuilder sb = new StringBuilder();
        // 遍历所有参数
        for(int i = 0; i < parameters.length; i++) {
            // 2个不同对象getName()区别
            // args:com.imp.dto.Person
            // param:[Ljava.lang.reflect.Parameter;
                if (args[i] != null && isObj(args[i])) {
                    sb.append(VerifyUtil.objVerify(parameters[i], args[i]));
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

    // 判断是不是自己的对象
    private boolean isObj(Object object) {
        if(object instanceof Integer || object instanceof String || object instanceof  Double
                || object instanceof Float || object instanceof  Character
                || object instanceof Long || object instanceof Short
                || object instanceof Boolean || object instanceof Byte) {
            return false;
        }
        return true;
    }

}
