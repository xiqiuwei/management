package com.management.controlleradvice.demo.advicetest;

import com.management.controlleradvice.demo.entity.Student;
import com.management.controlleradvice.demo.exception.MyException;
import com.management.controlleradvice.demo.exception.ViewException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author xiqiuwei
 * @Date Created in 16:41 2019/8/5
 * @Description
 * @Modified By:
 */
@Controller
@RequestMapping("/exception")
public class MyControllerAdvice {

    @PostMapping("/advice")
    public String adviceDemo (@RequestBody Student student) {
       if ("三土".equals(student.getName()) || null == student.getName()) {
            throw new MyException("三土打篮球像菜虚困",10086);
       }else {
           return "SUCCESS";
       }
    }

    @GetMapping(value = "/test")
    public ModelAndView test (@RequestParam String name) {
        ModelAndView error = new ModelAndView("error");
        error.addObject("MESSAGE","santu");
        error.addObject("CODE", 1000);
        if ("三土".equals(name)){
            return error;
        }
        throw new ViewException("报错了",1);
    }
}
