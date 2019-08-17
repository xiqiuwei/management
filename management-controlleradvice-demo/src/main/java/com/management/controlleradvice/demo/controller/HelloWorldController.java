package com.management.controlleradvice.demo.controller;

import com.management.controlleradvice.demo.entity.ResponseEntity;
import com.management.controlleradvice.demo.entity.Student;
import com.management.controlleradvice.demo.filter.MyInterceptor;
import com.management.controlleradvice.demo.service.IHelloWorldService;
import management.auth.common.entity.User;
import management.auth.common.entity.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xiqiuwei
 * @Date Created in 15:50 2019/8/15
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping("/helloWorld")
public class HelloWorldController {

    @Autowired
    private IHelloWorldService helloWorldService;

    @GetMapping("/getString")
    public ResponseEntity<Student> getHelloWorld (@RequestParam("id")String id) {
        Student student = helloWorldService.getDescriptionById(id);
        return ResponseEntity.success(student);
    }
}
