package com.management.redis.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Objects;

/**
 * @Author xiqiuwei
 * @Date Created in 15:06 2019/10/1
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping("test")
public class RedisTest {

    // reids原子操作
    private final String LUA = "local number = ARGV[1] \n" +
            "local gsave = redis.call('hget','goods_'..KEYS[1],'gsave') \n" +
            "if gsave < number then \n" +
            "return 2 \n" +
            "end\n" +
            "gsave = gsave - number \n" +
            "redis.call('hset','goods_'..KEYS[1],'gsave',gsave)\n" +
            "redis.call('rpush','orders_'..KEYS[1],ARGV[2])\n" +
            "if gsave <= 0 then\n" +
            "return 0\n" +
            "else \n" +
            "return 1\n" +
            "end";

    @Autowired
    private RedisTemplate redisTemplate;

    private String sha1 = null;

    @PostMapping("miaosha")
    public String miaosha(@RequestBody OrderInfoDTO orderInfoDTO) {
        Jedis jedis = (Jedis) Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().getNativeConnection();
        if (null == sha1) {
            sha1 = jedis.scriptLoad(this.LUA);
        }
        String orderInfo = orderInfoDTO.getOrderNumber() + "-" + System.currentTimeMillis();
        // redis key的数量
        Long result = (Long) jedis.evalsha(sha1, 100, orderInfoDTO.getUserId(), orderInfoDTO.getUUID(), orderInfo);
        if (0 == result) {
            // 异步执行数据库的插入操作
        }
        return "SUCCESS";
    }
}
