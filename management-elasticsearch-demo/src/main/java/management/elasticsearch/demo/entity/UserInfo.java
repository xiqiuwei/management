package management.elasticsearch.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Author xiqiuwei
 * @Date Created in 14:58 2019/8/22
 * @Description elasticsearch中的索引对象
 * @Modified By:
 */
@Data
@Document(indexName = "userinfo",type = "docs")
public class UserInfo {
    @Id
    private Long userId;
    private String userName;
    private int age;
}
