package management.mongodb.demo.controller;

import com.mongodb.session.ClientSession;
import management.mongodb.demo.entity.ResponseEntity;
import management.mongodb.demo.entity.Student;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.HashAttributeSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xiqiuwei
 * @Date Created in 9:40 2019/8/20
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping("mongoOperation")
public class MongoTestController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    @PostMapping("insert")
    public ResponseEntity<String> insertStudentInMongoDB(@RequestBody Student student) {
        // mongo的insert方法是如果存在相同id了抛异常DuplicateKeyException
        // mongo的save是如果存在就更新，不存在就新增
        List<Student> objects = new ArrayList<>();
        objects.add(student);
        long l = System.currentTimeMillis();
        //mongoTemplate.insert(student);
        mongoTemplate.insert(objects,Student.class);
        long l1 = System.currentTimeMillis() - l;
        System.out.println(l1);
            return ResponseEntity.success("SUCCESS");
    }

    @Transactional
    @DeleteMapping("delete/{id}")
    public ResponseEntity<List<Student>> deleteStudentById(@PathVariable String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        List<Student> studentList = mongoTemplate.findAllAndRemove(query, Student.class);
        if (studentList != null) {
            return ResponseEntity.success(studentList);
        }else {
            return ResponseEntity.fail("删除失败",-1);
        }
    }
}
