package management.mongodb.demo.client.fallback;

import management.mongodb.demo.client.HelloWorldClient;
import management.mongodb.demo.entity.ResponseEntity;
import management.mongodb.demo.entity.Student;
import org.springframework.stereotype.Component;


/**
 * @Author xiqiuwei
 * @Date Created in 13:45 2019/8/16
 * @Description
 * @Modified By:
 */
@Component
public class HelloWorldFallBack implements HelloWorldClient {
    @Override
    public ResponseEntity<Student> getHelloWorld(String id) {
        Student student = new Student();
        student.setMessage("服务熔断》》》》三土乔碧萝公主殿下也是你敢想的任务？继续回去搬砖去！！！");
        return ResponseEntity.success(student);
    }
}
