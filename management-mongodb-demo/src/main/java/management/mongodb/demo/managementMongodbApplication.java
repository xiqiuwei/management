package management.mongodb.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author xiqiuwei
 * @Date Created in 10:58 2019/8/16
 * @Description
 * @Modified By:
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class managementMongodbApplication {
    public static void main(String[] args) {
        SpringApplication.run(managementMongodbApplication.class, args);
    }
}
