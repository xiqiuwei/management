package management.auth.common.gettoken;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import management.auth.common.entity.ErrorCode;
import management.auth.common.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
@SuppressWarnings("all")
/**
 * @Author xiqiuwei
 * @Date Created in 9:10 2019/8/9
 * @Description
 * @Modified By:
 */
@Slf4j
public class JwtUtils {

    private RSAUtils rsaUtils = new RSAUtils();

    /**
     * @return java.lang.String
     * @Author xiqiuwei
     * @Date 20:07  2019/8/12
     * @Param []
     * @Description 后端自定义token将用户的信息放在载荷当中，返回给前端，
     * 每次前端请求的时候都放在header中传到后端进行解析这里
     * 存在一个以为，RSA加密token只能用私钥加密，公钥报错；
     */
    public String createToken(String userName, String password) throws Exception {
        RSAPrivateKey privateKey = rsaUtils.getPrivateKey();
        // 用户名和密码是数据库里面的数据
        // 可以将生成的token放到缓存中加TTL，当用户每次请求接口时刷新token的TTL
        long currentTime = System.currentTimeMillis();
        long expiration = 600000L;
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .claim("userName", userName) // 数据库当中的用户信息
                .claim("password", password) // 数据库中的用户的密码
                .setIssuedAt(new Date(currentTime))
                .setSubject("APP")
                .setAudience("SERVICE")
                .setExpiration(new Date(currentTime + expiration))
                .compact();
        return "Bearer "+ token;
    }

    /**
     * @return void
     * @Author xiqiuwei
     * @Date 9:56  2019/8/13
     * @Param [token]
     * @Description 可以将此方法写在aop或者拦截器，过滤器当中拿到请求中的Authorization
     * 解析token中的载荷信息，当然还需要判断token的有效时间是否过期；
     */
    public User parseToken(String token) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        RSAPublicKey publicKey = rsaUtils.getPublicKey();
        Claims claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
            String userName = (String) claims.get("userName");
            String password = (String) claims.get("password");
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            return user;
    }


    /*private void verifyToken(String token, RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.RSA256(rsaPublicKey, rsaPrivateKey)).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new CommonException("token过期了请重新登录",-1);
        }
    }*/

    // token包含三部分，JWT头，有效载荷，签名
   /* public static void main(String[] args) throws Exception {
       *//* String token = new JwtUtils().createToken();
        System.out.println("这是后端生成的token: "+token);*//*
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyTmFtZSI6IjE4MzIxOTExMTAzIiwicGFzc3dvcmQiOiJ4aXFpdXdlaSIsImlhdCI6MTU2NTg2NDM3MSwic3ViIjoiQVBQIiwiYXVkIjoiU0VSVklDRSIsImV4cCI6MTU2NTg2NDk3MX0.XD_bAadMGMcuaLhb4cGbkLPSBZrrf-epTDdCDRYeGiyqA9Jt1Fe8oBgXoeoh5w9iu3whJA6jPNrIGuBnRC3Yn1l7fNvwQEoxrXSfEGBzKnJPCu1xsqD-vMB2kkLLHaGyVcudwQCXYDAXdhN-MpK-pTi7Jq2SsCFnPUB8UBGNAPQ";
        User user = new JwtUtils().parseToken(token);

        System.out.println("这是token中解析出来的用户名:" + user.getUserName());
        System.out.println("这是token中解析出来的密码:" + user.getPassword());
    }*/
    /**
     * @return void
     * @Author xiqiuwei
     * @Date 16:01  2019/8/13
     * @Param [response, errorCode]
     * @Description 返回自定义错误信息
     */
    public void render(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = objectMapper.writeValueAsString(errorCode);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }

}
