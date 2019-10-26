package com.management.topdf.demo.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author xiqiuwei
 * @Date Created in 21:45 2019/9/8
 * @Description
 * @Modified By:
 */
public class Santuer implements MethodInterceptor {

    public Object getInstance (Object object) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    // 这里同样做了字节码重组
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("这是cglib代理");
        // 这里必须要调用父类的，如果用invoke会出现死循环
        methodProxy.invokeSuper(o,objects);
        System.out.println("有3000万的存款");
        return null;
    }
}
