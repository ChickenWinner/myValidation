package com.imp.exception;

/**
 * 参数处理异常
 *
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2019/3/4 13:46
 */
public class VerifyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public VerifyException(String code, String s) {
        this.code = code;
        this.msg = s;
    }

    public VerifyException(String message) {
        super(message);
    }

    public VerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifyException(Throwable cause) {
        super(cause);
    }

    protected VerifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
