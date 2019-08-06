package management.eureka.cluster.two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // 注册中心服务端注解
public class ManagementEurekaClusterTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementEurekaClusterTwoApplication.class, args);
    }

}
