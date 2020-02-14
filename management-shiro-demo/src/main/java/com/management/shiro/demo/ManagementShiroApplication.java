package com.management.shiro.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author xiqiuwei
 * @Date Created in 22:06 2019/12/4
 * @Description
 * @Modified By:
 */
@MapperScan({"com.management.shiro.demo.dao"})
@SpringBootApplication
public class ManagementShiroApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementShiroApplication.class, args);
    }
}
