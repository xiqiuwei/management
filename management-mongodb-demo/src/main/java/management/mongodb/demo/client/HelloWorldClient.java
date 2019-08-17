package management.mongodb.demo.client;

import management.mongodb.demo.client.fallback.HelloWorldFallBack;
import management.mongodb.demo.entity.ResponseEntity;
import management.mongodb.demo.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author xiqiuwei
 * @Date Created in 13:43 2019/8/16
 * @Description
 * @Modified By:
 */
@Component
@FeignClient(name = "advice_server",fallback = HelloWorldFallBack.class)
public interface HelloWorldClient {

    @GetMapping("/getString")
    ResponseEntity<Student> getHelloWorld (@RequestParam("id")String id);

}
