package com.management.redis.demo.interfaces;

import java.lang.annotation.*;

/**
 * @Author xiqiuwei
 * @Date Created in 15:26 2020/2/12
 * @Description
 * @Modified By:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {
    int lockTime() default 5000;
}
