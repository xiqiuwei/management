package com.management.controlleradvice.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xiqiuwei
 * @Date Created in 16:40 2019/8/5
 * @Description
 * @Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private int age;
    private String gender;
}
