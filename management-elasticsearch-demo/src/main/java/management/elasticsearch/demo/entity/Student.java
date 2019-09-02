package management.elasticsearch.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Author xiqiuwei
 * @Date Created in 8:45 2019/8/27
 * @Description
 * @Modified By:
 */
@Data
@Document(indexName = "student_info",type = "student")
public class Student {
    @Id
    private String id;
    private String name;
    private int age;
    private String subject;
    private Teacher teacher;
}
