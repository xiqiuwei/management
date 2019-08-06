package com.management.controlleradvice.demo.handler;

import com.management.controlleradvice.demo.exception.MyException;
import com.management.controlleradvice.demo.exception.ViewException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author xiqiuwei
 * @Date Created in 16:42 2019/8/5
 * @Description
 * @Modified By:
 */
@ControllerAdvice
public class MyAdvice {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Map classNotFoundException(Exception e) {
        Map map = new HashMap();
        map.put("错误代码", 500);
        if (e instanceof MyException) {
            map.put("这是错误的异常信息", e.getMessage());
        } else if (e instanceof Exception) {
            map.put("这是人们从未发现的错误", e.getMessage());
        }
        return map;
    }


    @ExceptionHandler(ViewException.class)
    public String viewAdvice(ViewException e) {
        return "404";
    }
}
