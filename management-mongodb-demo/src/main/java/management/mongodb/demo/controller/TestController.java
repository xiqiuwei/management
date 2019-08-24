package management.mongodb.demo.controller;

import com.netflix.discovery.converters.Auto;
import management.mongodb.demo.client.HelloWorldClient;
import management.mongodb.demo.entity.ResponseEntity;
import management.mongodb.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xiqiuwei
 * @Date Created in 11:58 2019/8/24
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private HelloWorldClient helloWorldClient;

    @GetMapping("demo")
    public ResponseEntity<Student> feignTest (@RequestParam("id") String id) {
        return helloWorldClient.getHelloWorld(id);
    }
}
