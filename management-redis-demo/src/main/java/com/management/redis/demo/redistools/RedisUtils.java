package com.management.redis.demo.redistools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@SuppressWarnings("all")
/**
 * @Author xiqiuwei
 * @Date Created in 15:11 2019/8/1
 * @Description
 * @Modified By:
 */
@Component
public class RedisUtils {
    /*
    redisTemplate.opsForValue() ：操作字符串
    redisTemplate.opsForHash() ：操作hash
    redisTemplate.opsForList() ：操作list
    redisTemplate.opsForSet() ：操作set
    redisTemplate.opsForZSet() ：操作有序set
    */

    @Autowired
    private JedisPool jedisPool;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 返还到连接池
     *
     * @param jedisPool
     * @param jedis
     */
    public static void returnResource(JedisPool jedisPool, Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * @param key
     * @param index 选择哪一个库
     * @return
     */
    public String get(String key, int index) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(index);
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedisPool, jedis);
        }
        return value;
    }

    public byte[] get(byte[] key, int index) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(index);
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedisPool, jedis);
        }
        return value;
    }

    /**
     * 向redis存入key和value,并释放连接资源
     * 如果key已经存在 则覆盖
     *
     * @param key
     * @param value
     * @param indexdb
     * @return
     */
    public String set(String key, String value, int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.set(key, value);
        } catch (Exception e) {
            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * 向redis存入key和value,并释放连接资源
     * 如果key已经存在 则覆盖
     *
     * @param key
     * @param value
     * @param indexdb 选择redis库 0-15
     * @return 成功 返回OK 失败返回 0
     */
    public String set(byte[] key, byte[] value, int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

}
