package com.management.controlleradvice.demo.exception;

import lombok.Data;

/**
 * @Author xiqiuwei
 * @Date Created in 18:53 2019/8/5
 * @Description
 * @Modified By:
 */
@Data
public class ViewException extends RuntimeException {
    private int code;

    public ViewException (String msg, int code) {
        super(msg);
        this.code = code;
    }
}
