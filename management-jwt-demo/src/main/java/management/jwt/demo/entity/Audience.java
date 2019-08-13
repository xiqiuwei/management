package management.jwt.demo.entity;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xiqiuwei
 * @Date Created in 18:15 2019/8/8
 * @Description
 * @Modified By:
 */
@Data
@Configuration
public class Audience {

    private String clientId;
    private String base64Secret;
    private String name;
    private String expiresSecond;

}
