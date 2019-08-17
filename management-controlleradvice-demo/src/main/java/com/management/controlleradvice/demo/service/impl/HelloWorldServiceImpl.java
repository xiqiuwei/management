package com.management.controlleradvice.demo.service.impl;

import com.management.controlleradvice.demo.entity.ResponseEntity;
import com.management.controlleradvice.demo.entity.Student;
import com.management.controlleradvice.demo.filter.MyInterceptor;
import com.management.controlleradvice.demo.service.IHelloWorldService;
import org.springframework.stereotype.Service;
@SuppressWarnings("ALL")
/**
 * @Author xiqiuwei
 * @Date Created in 10:48 2019/8/16
 * @Description
 * @Modified By:
 */

@Service
public class HelloWorldServiceImpl implements IHelloWorldService {
    @Override
    public Student getDescriptionById(String id) {
        Student student = new Student();
        student.setName("这是经过鉴权中心校验后得到的有效token解析的数据:"+ MyInterceptor.getLoginUser().getUserName());
        student.setPassword("这是经过鉴权中心校验后得到的有效token解析的数据:"+MyInterceptor.getLoginUser().getPassword());
        if (id.equals("1")) {
            student.setMessage("三土打篮球像菜虚困一样美");
            return student;
        }
        if (id.equals("2")) {
            student.setMessage("三土打篮球真帅,比菜虚困都帅");
            return student;
        }else {
            student.setMessage("三土这个禽兽竟然和乔碧萝公主殿下。。。。。。");
            return student;
        }
    }
}
