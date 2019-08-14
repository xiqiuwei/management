/*
package management.jwt.demo.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import management.jwt.demo.entity.ErrorCode;
import management.jwt.demo.gettoken.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
@SuppressWarnings("all")
*/
/**
 * @Author xiqiuwei
 * @Date Created in 13:36 2019/8/13
 * @Description 对请求进行拦截，判断token是否有效,有了springcloud的zuulFilter所以就放弃了拦截器
 * @Modified By:
 *//*


public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 每次请求头部token都会放在Authorization:Bearer+空格(JWT定义的规范,可以解决cors)
        String authorization = request.getHeader("Authorization");
        try {
            if (StrUtil.isBlank(authorization)) {
                render(response, ErrorCode.AuthorizationEmpty);
                return false;
            }
            if (!authorization.startsWith("Bearer ")) {
                render(response, ErrorCode.Bearer);
                return false;
            }
            final String token = authorization.substring(7);
            // 解析token将载荷信息存放到本地线程
            new JwtUtils().parseToken(token);
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(HttpStatus.UNAUTHORIZED.value());
            e.printStackTrace();
        }
        return true;
    }


    */
/**
     * @return void
     * @Author xiqiuwei
     * @Date 16:01  2019/8/13
     * @Param [response, errorCode]
     * @Description 返回自定义错误信息
     *//*

    private void render(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = objectMapper.writeValueAsString(errorCode);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
*/
