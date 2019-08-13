package management.jwt.demo.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author xiqiuwei
 * @Date Created in 16:04 2019/8/13
 * @Description
 * @Modified By:
 */
@EnableWebMvc
@Configuration
public class MvcConfigurer extends WebMvcConfigurationSupport {
    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }
    /**
     *@Author xiqiuwei
     *@Date 16:08  2019/8/13
     *@Param [registry]
     *@return void
     *@Description 注册当前的拦截器，并且忽略登录的接口
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .excludePathPatterns("/login/getToken");
        super.addInterceptors(registry);
    }
}
