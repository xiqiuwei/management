package management.mongodb.demo.controller;

import management.mongodb.demo.client.HelloWorldClient;
import management.mongodb.demo.entity.ResponseEntity;
import management.mongodb.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.core.env.Environment;
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
    @Autowired
    private Environment env;
    @GetMapping("demo")
    public ResponseEntity<Student> feignTest (@RequestParam("id") String id) {
        Integer property = env.getProperty("123", Integer.class);
        Double property1 = env.getProperty("456", Double.class);
        return helloWorldClient.getHelloWorld(id);
    }

    public void findBoyFriend () {

    }
}
