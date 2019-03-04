package com.imp.dto;

/**
 * 封装结果dto
 *
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/4 13:59
 */
public class ResultDto<T> {
    // 结果信息
    private String msg;

    // 结果码
    private String code;

    // 返回数据
    private T data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
