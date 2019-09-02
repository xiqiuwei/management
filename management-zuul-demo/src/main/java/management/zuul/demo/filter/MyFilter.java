package management.zuul.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import management.auth.common.gettoken.JwtUtils;
import management.auth.common.entity.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SuppressWarnings("ALL")
/**
 * @Author xiqiuwei
 * @Date Created in 16:30 2019/8/14
 * @Description
 * @Modified By:
 */
@Slf4j
@Component
@EnableConfigurationProperties(FilterProperties.class)
public class MyFilter extends ZuulFilter {

    @Autowired
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * @return boolean
     * @Author xiqiuwei
     * @Date 18:07  2019/8/14
     * @Param []
     * @Description true说明需要过滤，false说明不需要过滤
     */
    @Override
    public boolean shouldFilter() {
        // 获取上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 获取请求
        HttpServletRequest request = currentContext.getRequest();
        // 获取请求路径
        String requestURI = request.getRequestURI();
        // 如果不是白名单的uri就返回true需要过滤
        return isAllowUri(requestURI);
    }

    /**
     * @return java.lang.Object
     * @Author xiqiuwei
     * @Date 18:08  2019/8/14
     * @Param []
     * @Description run方法是具体执行过滤的逻辑
     */
    @Override
    public Object run() throws ZuulException {
        // 获取上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        // 每次请求头部token都会放在Authorization:Bearer+空格(JWT定义的规范,可以解决cors)
        String authorization = request.getHeader("Authorization");
        try {
            if (authorization == null) {
                render(response, ErrorCode.AuthorizationEmpty);
            }
      /*      final String token = authorization.substring(7);
            // 解析token将载荷信息存放到本地线程
            new JwtUtils().parseToken(token);*/
        } catch (ExpiredJwtException e) {
            try {
                currentContext.setSendZuulResponse(false);
                currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                render(response, ErrorCode.TokenExpiration);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isAllowUri(String uri) {
        boolean flag = true;
        List<String> allowPaths = filterProperties.getAllowPaths();
        for (String allowPath : allowPaths) {
            if (uri.startsWith(allowPath)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * @return void
     * @Author xiqiuwei
     * @Date 16:01  2019/8/13
     * @Param [response, errorCode]
     * @Description 返回自定义错误信息
     */
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
