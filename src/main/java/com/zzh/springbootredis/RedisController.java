package com.zzh.springbootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("set")
    public String test() {
        redisTemplate.opsForHash().put("key1", "key2", "value2");
        redisTemplate.opsForHash().put("key1", "key3", "value3");
        redisTemplate.opsForHash().put("key1", "key2", "value4");
        redisTemplate.opsForList().rightPush("lk1", "111");
        redisTemplate.opsForList().rightPush("lk1", "222");
        return "test";
    }

    @GetMapping("get")
    public String get() {
        Map data = redisTemplate.opsForHash().entries("key1");
        data.forEach((k, v) -> {
            System.out.println(k.toString() + v.toString());
        });

        redisTemplate.opsForList().range("lk1", 0L, redisTemplate.opsForList().size("lk1"));
        for (Long i = 0L, n = redisTemplate.opsForList().size("lk1"); i < n; i++) {
            Object data2 = redisTemplate.opsForList().leftPop("lk1");
            System.out.println(data2.toString());
        }
        return "get";
    }
}
