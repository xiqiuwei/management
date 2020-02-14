package com.management.redis.demo.test;

import com.management.redis.demo.redistools.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author xiqiuwei
 * @Date Created in 13:57 2020/2/14
 * @Description
 * @Modified By:
 */

@Component
public class RedisReference {
    @Autowired
    private JedisPool pool;
    @Resource
    private RedisUtils redisUtils;

    public void decreaseAndIncrease() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            redisUtils.set("number", "100", 0, 10000L);
            Long number1 = jedis.decr("number");
            System.err.println("数字decr的结果:" + number1);
            Long number2 = jedis.incr("number");
            System.err.println("数字incr的结果:" + number2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.del("number");
        }
    }

    public void batchSetAndGet() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.mset("xiqiuwei", "handsome", "santuer", "uglyDuckling");
            List<String> list = jedis.mget("xiqiuwei", "santuer");
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                System.err.println(next);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.del("xiqiuwei", "santuer");
        }

    }

    public void setEx() {
        Jedis jedis = pool.getResource();
        jedis.psetex("name", 5000L, "王市长");
        Boolean exists = jedis.exists("name");
        if (exists) {
            String name = jedis.get("name");
            System.err.println("原来是大名鼎鼎的:" + name + "啊!!!");
        }
    }

    public void hashGenre() {
        Jedis jedis = pool.getResource();
        jedis.hset("person", "santuer", "bat-sashimi");
        jedis.hset("person", "wangshizhang", "eat-bat");
        jedis.hset("person", "liangwei", "bat-soup");
        String result = jedis.hget("person", "wangshizhang");
        System.err.println("具体的key-value:" + result);
        Map<String, String> person = jedis.hgetAll("person");
        Set<Map.Entry<String, String>> entries = person.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().getKey();
            String value = iterator.next().getValue();
            System.err.println(key + ":" + value);
        }
    }
}
