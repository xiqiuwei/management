package com.management.controlleradvice.demo.filter;

import cn.hutool.system.UserInfo;
import management.auth.common.entity.ErrorCode;
import management.auth.common.entity.User;
import management.auth.common.gettoken.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author xiqiuwei
 * @Date Created in 18:59 2019/8/15
 * @Description
 * @Modified By:
 */
public class MyInterceptor extends HandlerInterceptorAdapter {

    private static JwtUtils jwtUtils = new JwtUtils();
    private static final ThreadLocal<User> local = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorization)) {
            try {
                final String token = authorization.substring(7);
                User user = jwtUtils.parseToken(token);
                local.set(user);
            }catch (Exception e) {
                e.printStackTrace();
                jwtUtils.render(response, ErrorCode.OtherErrors);
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        local.remove();
    }

    /**
     * 对外提供一个获取当前线程登陆的用户信息的静态方法
     * @return
     */
    public static User getLoginUser(){
        return local.get();
    }
}
