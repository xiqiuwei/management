package management.jwt.demo.controller;

import management.auth.common.gettoken.JwtUtils;
import management.jwt.demo.entity.ResponseEntity;
import management.jwt.demo.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xiqiuwei
 * @Date Created in 15:46 2019/8/13
 * @Description CSRF XSS
 * @Modified By:
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/getToken")
    public ResponseEntity<String> getToken (@RequestBody User user) throws Exception {
        JwtUtils jwtUtils = new JwtUtils();
        // 数据库查出来的数据去判断是否有这个用户
        String token = jwtUtils.createToken("18321911103", "xiqiuwei");
       /* // 将token设置到redis里面设置TTL,如果不用刷新token的TTL的话直接在创建jwt的时候就设置过期时间
        stringRedisTemplate.opsForValue().set("login:user:18321911103",token,5, TimeUnit.MINUTES);*/
        return ResponseEntity.success(token);
    }
}
