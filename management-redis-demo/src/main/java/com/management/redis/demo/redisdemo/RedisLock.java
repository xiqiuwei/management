package com.management.redis.demo.redisdemo;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import java.util.Collections;

/**
 * @Author xiqiuwei
 * @Date Created in 16:17 2019/7/31
 * @Description
 * @Modified By:
 */
@Component
public class RedisLock {

    static {
        LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    }

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final String LUA;
    private static final Long RELEASE_SUCCESS = 1L;


    /**
     * @return boolean
     * @Author xiqiuwei
     * @Date 17:05  2019/7/31
     * @Param [jedis, lockKey, requestId, expiration]
     * @Description redis set命令
     *  第一个参数key作为redis的锁
     *  第二个参数requestId相当于分布式锁的一个标识，解锁的时候需要这个参数作为依据
     *  第三个参数SET_IF_NOT_EXIST就是当key不存在的时候才进行set操作(NX)
     *  第四个参数SET_WITH_EXPIRE_TIME声明要给当前的key加上一个过期时间(PX)
     *  第五个参数expiration就是实际给key加的过期时间
     *
     *  redis的上锁操作
     */
    public  boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expiration) {
        String response = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expiration);
        return LOCK_SUCCESS.equals(response);
    }

    /**
     * @return boolean
     * @Author xiqiuwei
     * @Date 17:49  2019/7/31
     * @Param [jedis, lockKey, requestId]
     * @Description redis eval命令 推送一段lua脚本给redis服务端保证了解锁的原子性
     *  参数KEYS[1]赋值为lockKey，ARGV[1]赋值为requestId
     *
     *  redis的解锁操作
     */
    public  boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        Object response = jedis.eval(LUA, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(response);
    }
}
