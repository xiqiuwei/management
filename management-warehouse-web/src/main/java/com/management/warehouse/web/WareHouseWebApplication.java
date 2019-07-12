package com.management.warehouse.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author xiqiuwei
 * @Date Created in 15:09 2019/7/12
 * @Description
 * @Modified By:
 */
@MapperScan({"com.management.warehouse.core.dao"})
@SpringBootApplication
@ComponentScan(basePackages = { "com.management.warehouse.**" })
public class WareHouseWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WareHouseWebApplication.class, args);
    }
}
