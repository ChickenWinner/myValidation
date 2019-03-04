package com.imp.utils;

import com.imp.dto.ResultDto;
import com.imp.enums.VerifyMsg;

/**
 * 结果工具类
 *
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/4 13:58
 */
public class ResultUtil {

    // 成功并且带数据
    public static <T> ResultDto success(T t) {
        ResultDto res = new ResultDto();
        res.setMsg(VerifyMsg.SUCCESS.getMessage());
        res.setCode(VerifyMsg.SUCCESS.getCode());
        res.setData(t);
        return res;
    }

    // 成功且不带数据
    public static ResultDto success() {
        return success(null);
    }

    // 失败
    public static ResultDto error(String code, String msg) {
        ResultDto res = new ResultDto();
        res.setMsg(msg);
        res.setCode(code);
        return res;
    }
}
