package management.eureka.cluster.three;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ManagementEurekaClusterThreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementEurekaClusterThreeApplication.class, args);
    }

}
