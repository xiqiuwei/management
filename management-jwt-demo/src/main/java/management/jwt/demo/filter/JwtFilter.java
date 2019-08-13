package management.jwt.demo.filter;

import management.jwt.demo.entity.Audience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @Author xiqiuwei
 * @Date Created in 18:11 2019/8/8
 * @Description
 * @Modified By:
 */
public class JwtFilter extends GenericFilterBean {

    @Autowired
    private Audience audience;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}
