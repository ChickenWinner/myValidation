package com.imp.utils;

import com.imp.dto.ResultDto;
import com.imp.exception.VerifyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常捕获器
 *
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/4 14:06
 */
@ControllerAdvice
@ResponseBody()
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResultDto myExceptionHandler(RuntimeException e) {
        //e.printStackTrace();
        // 如果是参数检验异常
        if (e instanceof VerifyException) {
            VerifyException v = (VerifyException) e;
            return ResultUtil.error(v.getCode(), v.getMsg());
        }
        return ResultUtil.success();
    }
}


