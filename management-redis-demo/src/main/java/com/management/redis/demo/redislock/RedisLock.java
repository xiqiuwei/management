package com.management.redis.demo.redislock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author xiqiuwei
 * @Date Created in 16:17 2019/7/31
 * @Description
 * @Modified By:
 */
@Component
@Slf4j
public class RedisLock {

    static {
        LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        // String LOCK_LUA = "if redis.call('setNx',KEYS[1],ARGV[1]) == 1 then if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end else return 0 end";
    }

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX"; // 毫秒
    private static final String LUA;
    private static final Long RELEASE_SUCCESS = 1L;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * @return boolean
     * @Author xiqiuwei
     * @Date 17:05  2019/7/31
     * @Param [jedis, lockKey, requestId, expiration]
     * @Description redis set命令
     * 第一个参数key作为redis的锁
     * 第二个参数requestId相当于分布式锁的一个标识，解锁的时候需要这个参数作为依据
     * 第三个参数SET_IF_NOT_EXIST就是当key不存在的时候才进行set操作(NX)
     * 第四个参数SET_WITH_EXPIRE_TIME声明要给当前的key加上一个过期时间单位(PX)
     * 第五个参数expiration就是实际给key加的过期时间
     * redis的上锁操作
     */
    public boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expiration) {
        String response = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expiration);
        log.info("这是尝试上锁的响应值:{}",response);
        return LOCK_SUCCESS.equals(response);
    }

    /**
     * @return boolean
     * @Author xiqiuwei
     * @Date 17:49  2019/7/31
     * @Param [jedis, lockKey, requestId]
     * @Description redis eval命令 推送一段lua脚本给redis服务端保证了解锁的原子性
     * 参数KEYS[1]赋值为lockKey，ARGV[1]赋值为requestId
     * redis的解锁操作
     */
    public boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        Object response = jedis.eval(LUA, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        log.info("这是释放锁的响应值:{}",response);
        return RELEASE_SUCCESS.equals(response);
    }

    /**
     * @return void
     * @Author xiqiuwei
     * @Date 14:13  2019/8/2
     * @Param [key, requestId]
     * @Description redisCluster用的锁
     */
    public void redisClusterLock(String key, String requestId) {
        List<String> keys = new ArrayList<>();
        keys.add(key);
        List<String> args = new ArrayList<>();
        args.add(requestId);
        //spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本异常，此处拿到原redis的connection执行脚本
        String result = (String) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Object nativeConnection = connection.getNativeConnection();
            // 集群模式和单点模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
            // 集群
            if (nativeConnection instanceof JedisCluster) {
                return (Boolean) ((JedisCluster) nativeConnection).eval(LUA, keys, args);
            }
            // 单点
            else if (nativeConnection instanceof Jedis) {
                return (Boolean) ((Jedis) nativeConnection).eval(LUA, keys, args);
            }
            return null;
        });
        System.out.println("这是加锁后的结果: " + result);
    }
}