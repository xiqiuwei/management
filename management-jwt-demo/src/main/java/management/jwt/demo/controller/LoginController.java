package management.jwt.demo.controller;

import management.jwt.demo.entity.ResponseEntity;
import management.jwt.demo.entity.User;
import management.jwt.demo.gettoken.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author xiqiuwei
 * @Date Created in 15:46 2019/8/13
 * @Description
 * @Modified By:
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/getToken")
    public ResponseEntity<String> getToken (@RequestBody User user) throws Exception {
        JwtUtils jwtUtils = new JwtUtils();
        // 数据库查出来的数据去判断是否有这个用户
        String token = jwtUtils.createToken("18321911103", "xiqiuwei");
        // 将token设置到redis里面设置TTL
        stringRedisTemplate.opsForValue().set("login:user:18321911103",token,5, TimeUnit.MINUTES);
        return ResponseEntity.success(token);
    }
}
