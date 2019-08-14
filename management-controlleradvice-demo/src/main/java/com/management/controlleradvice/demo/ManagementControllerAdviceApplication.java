package com.management.controlleradvice.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author xiqiuwei
 * @Date Created in 16:29 2019/8/5
 * @Description
 * @Modified By:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ManagementControllerAdviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementControllerAdviceApplication.class,args);
    }
}
