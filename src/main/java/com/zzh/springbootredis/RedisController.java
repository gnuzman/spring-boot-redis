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

    @GetMapping("Map")
    public String redisMap() {
        redisTemplate.opsForHash().put("key1", "key2", "value2");
        redisTemplate.opsForHash().put("key1", "key3", "value3");
        redisTemplate.opsForHash().put("key1", "key2", "value4");

        StringBuilder builder = new StringBuilder();

        Map data = redisTemplate.opsForHash().entries("key1");
        data.forEach((k, v) -> {
            builder.append(k.toString());
            builder.append(v.toString());
        });

        return builder.toString();
    }


    @GetMapping("List")
    public String redisList() {
        redisTemplate.opsForList().rightPush("lk1", "111");
        redisTemplate.opsForList().rightPush("lk1", "222");

        StringBuilder builder = new StringBuilder();
        redisTemplate.opsForList().range("lk1", 0L, redisTemplate.opsForList().size("lk1")).forEach(value -> {
            builder.append(value.toString());
        });

        return builder.toString();
    }

    @GetMapping("zset")
    public String redisZSet() {
        redisTemplate.opsForZSet().add("classB", "user1", 100);
        redisTemplate.opsForZSet().add("classA", "user1", 19);
        redisTemplate.opsForZSet().add("classA", "user2", 22);
        redisTemplate.opsForZSet().add("classA", "user1", 11);
        redisTemplate.opsForZSet().add("classA", "user2", 29);

        StringBuilder builder = new StringBuilder();

        redisTemplate.opsForZSet().rangeWithScores("classA", 0L, 1L);
//        data.forEach((k, v) -> {
//            builder.append(k.toString());
//            builder.append(v.toString());
//        });

        return builder.toString();
    }
}
