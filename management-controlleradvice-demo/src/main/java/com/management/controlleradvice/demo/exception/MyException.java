package com.management.controlleradvice.demo.exception;

import lombok.Data;

/**
 * @Author xiqiuwei
 * @Date Created in 16:36 2019/8/5
 * @Description
 * @Modified By:
 */
@Data
public class MyException extends RuntimeException{
    private int code;

    public MyException (String message, int code) {
        super(message);
        this.code = code;
    }
}
