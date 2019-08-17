package com.management.controlleradvice.demo.service;

import com.management.controlleradvice.demo.entity.Student;

/**
 * @Author xiqiuwei
 * @Date Created in 10:46 2019/8/16
 * @Description feign的测试调用接口
 * @Modified By:
 */
public interface IHelloWorldService {

    Student getDescriptionById (String id);
}
