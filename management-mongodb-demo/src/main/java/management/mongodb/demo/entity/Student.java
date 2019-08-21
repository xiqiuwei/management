package management.mongodb.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xiqiuwei
 * @Date Created in 16:40 2019/8/5
 * @Description
 * @Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String id;
    private String name;
    private String password;
    private String message;
}
