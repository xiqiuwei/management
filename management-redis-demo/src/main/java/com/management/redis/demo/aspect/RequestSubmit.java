package com.management.redis.demo.aspect;

import com.management.redis.demo.redislock.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author xiqiuwei
 * @Date Created in 15:28 2020/2/12
 * @Description
 * @Modified By:
 */
@Aspect
@Component
public class RequestSubmit {
    private static final Logger log = LoggerFactory.getLogger(RequestSubmit.class);

    @Autowired
    private RedisLock lock;
    @Autowired
    private JedisPool pool;

    @Pointcut("@annotation(com.management.redis.demo.interfaces.RedisLock)")
    public void redisLock() {
    }

    @Around("redisLock()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        com.management.redis.demo.interfaces.RedisLock annotation = method.getAnnotation(com.management.redis.demo.interfaces.RedisLock.class);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Assert.notNull(request, "参数不能为空");
        String lockKey = request.getParameter("id");
        System.err.println("这是锁的key:" + lockKey);
        String requestId = UUID.randomUUID().toString();
        Jedis jedis = pool.getResource();
        int lockTime = annotation.lockTime();
        System.err.println("锁的自动释放时间:" + lockTime);
        // 尝试上锁
        boolean result = lock.tryGetDistributedLock(jedis, lockKey, requestId, lockTime);
        if (result) {
            Object obj;
            try {
                // 执行方法
                obj = pjp.proceed();
            } finally {
                try {
                    boolean release = lock.releaseDistributedLock(jedis, lockKey, requestId);
                    if (!release) {
                        log.info("redis锁释放失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return obj;
        } else {
            throw new RuntimeException("接口重复请求");
        }
    }
}
