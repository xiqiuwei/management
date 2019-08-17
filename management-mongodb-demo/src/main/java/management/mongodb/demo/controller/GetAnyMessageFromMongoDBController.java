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
 * @Date Created in 14:24 2019/8/16
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping("/getMessage")
public class GetAnyMessageFromMongoDBController {
    @Autowired
    private HelloWorldClient helloWorldClient;

    @GetMapping("/studentInfo")
    public ResponseEntity<Student> getAnyMessage(@RequestParam(value = "id", required = true) String id,
                                                 @RequestParam(value = "mobile", required = false) String mobile) {
        Student student = new Student();

        ResponseEntity<Student> helloWorld = helloWorldClient.getHelloWorld(id);
        // feign远程调用接口获取的数据
        String message = helloWorld.getData().getMessage();
        //TODO 根据id去mongoDB去查找信息
        return ResponseEntity.success(student);
    }
}
