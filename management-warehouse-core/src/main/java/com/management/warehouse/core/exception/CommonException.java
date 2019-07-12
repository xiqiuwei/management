package com.management.warehouse.core.exception;

/**
 * @Author xiqiuwei
 * @Date Created in 15:42 2019/5/27
 * @Description 如果涉及到跨城市的话需要抛出异常并且给予用户提示信息
 * @Modified By:
 */
public class CommonException extends RuntimeException {

    private static long serialVersionUID = 31011519930930L;

    private Integer errorCode;
    private String msg;

    public CommonException(String message, Integer code) {
        this.msg = message;
        this.errorCode = code;
    }

    public CommonException(String message, Throwable t) {
        super(message,t);
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable t, Integer errorCode) {
        super(message,t);
        this.setErrorCode(errorCode);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    private void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
