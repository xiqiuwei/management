package management.zuul.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient // 发现客户端注册中心
@EnableZuulProxy // 网关
public class ManagementZuulDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementZuulDemoApplication.class, args);
    }

}
