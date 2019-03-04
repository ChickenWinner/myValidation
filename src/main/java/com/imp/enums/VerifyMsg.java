package com.imp.enums;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/4 13:51
 */
public enum  VerifyMsg {


    NOT_PASS_VERIFI("10001","参数校验失败"),
    SUCCESS("10000","成功") ;

    private String message; // 信息

    private String code ; // 状态码

    VerifyMsg(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
