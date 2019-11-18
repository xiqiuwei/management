package com.management.spring.security.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author xiqiuwei
 * @Date Created in 22:20 2019/10/28
 * @Description
 * @Modified By:
 */

@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(public * com.management.spring.security.controller.*.*(..))")
    public void pointCut(){}

//    @Before("pointCut()")
//    public void aroundAspect(JoinPoint joinPoint) throws Throwable {
//        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        ModelAndView model = new ModelAndView("priceRule");
//        // 起始价格
//        model.addObject("startPrice", "100");
//        // 起始重量
//        model.addObject("startWeight", "100");
//        // 超出每件公斤数后的每公斤加价
//        model.addObject("outPrice", "100");
//        // 计价配置覆盖区域
//        model.addObject("coverageArea", "100");
//        request.setAttribute("param",model);
//    }

    @Around("pointCut()")
    public ModelAndView aroundAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        ModelAndView model = new ModelAndView("priceRule");
        model.addObject("startPrice", "100");
        model.addObject("startWeight", "100");
        model.addObject("outPrice", "100");
        model.addObject("coverageArea", "100");
        // request.setAttribute("param",model);
        return model;

    }
}
