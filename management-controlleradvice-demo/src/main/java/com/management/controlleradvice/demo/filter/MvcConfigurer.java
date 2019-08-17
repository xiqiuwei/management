package com.management.controlleradvice.demo.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author xiqiuwei
 * @Date Created in 19:02 2019/8/15
 * @Description
 * @Modified By:
 */
@EnableWebMvc
@Component
public class MvcConfigurer implements WebMvcConfigurer {
    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }

    /**
     * 添加登陆拦截器，并设置匹配路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor()).addPathPatterns("/**");
    }
}
