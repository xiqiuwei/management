package management.jwt.demo.gettoken;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import management.jwt.demo.entity.User;
import management.jwt.demo.entity.UserThreadLocal;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * @Author xiqiuwei
 * @Date Created in 9:10 2019/8/9
 * @Description
 * @Modified By:
 */
@Component
public class JwtUtils {

    private RSAUtils rsaUtils = new RSAUtils();

    /**
     * @return java.lang.String
     * @Author xiqiuwei
     * @Date 20:07  2019/8/12
     * @Param []
     * @Description 后端自定义token将用户的信息放在载荷当中，返回给前端，
     *              每次前端请求的时候都放在header中传到后端进行解析这里
     *              存在一个以为，RSA加密token只能用私钥加密，公钥报错；
     */
    public String createToken() throws Exception {

        InputStream inputStream = new ClassPathResource("/static/rsa.pri").getInputStream();
        String s = rsaUtils.readFile(inputStream);
        RSAPrivateKey privateKey = rsaUtils.getPrivateKey(s);
        // 用户名和密码是数据库里面的数据
        // 可以将生成的token放到缓存中加TTL，当用户每次请求接口时刷新token的TTL
        return Jwts.builder()
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .claim("userName", "18321911103") // 数据库当中的用户信息
                .claim("password", "123456") // 数据库中的用户的密码
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject("APP")
                .setAudience("SERVICE")
                .compact();
    }

    /**
     * @return void
     * @Author xiqiuwei
     * @Date 9:56  2019/8/13
     * @Param [token]
     * @Description 可以将此方法写在aop或者拦截器，过滤器当中拿到请求中的Authorization
     *              解析token中的载荷信息，当然还需要判断token的有效时间是否过期；
     */
    public void parseToken(String token) throws Exception {
        InputStream inputStream = new ClassPathResource("/static/rsa.pub").getInputStream();
        String s = rsaUtils.readFile(inputStream);
        RSAPublicKey publicKey = rsaUtils.getPublicKey(s);
        Claims claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        String userName = (String) claims.get("userName");
        String password = (String) claims.get("password");
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        UserThreadLocal.setLocalUser(user);
    }

    // token包含三部分，JWT头，有效载荷，签名
    public static void main(String[] args) throws Exception {
       /* String token = new JwtUtils().createToken();
        System.out.println("这是后端生成的token: "+token);*/
        String token = "eyJhbGciOiJSUzI1NiJ9." +
                "eyJ1c2VyTmFtZSI6IjE4MzIxOTExMTAzIiwicGFzc3dvcmQiOiIxMjM0NTYiLCJpYXQiOjE1NjU2NTg2MzIsInN1YiI6IkFQUCIsImF1ZCI6IlNFUlZJQ0UifQ." +
                "SOx-BQ5Wlhmam3QP0srHwcB1zIBzZw7srHuyQ_p6Y12CgmiPNfu2WwV_i2DYSgjl_QHwxBddtjSGie4Q4zuHKZUeGbekDt76AmYgX_w7ACzOIR5IGyiVFLFdUa37h_KWDwSn_afXqAnZySl-1yiqqUBdAHzmHA1mY7MNmm3QZuU";
        new JwtUtils().parseToken(token);
        User localUser = UserThreadLocal.getLocalUser();
        System.out.println("这是token中解析出来的用户名:" + localUser.getUserName());
        System.out.println("这是token中解析出来的密码:" + localUser.getPassword());
    }

}
