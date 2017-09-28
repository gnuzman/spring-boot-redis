package com.zzh.springbootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RedisStringController {
    @Autowired
    private RedisTemplate redisTemplate;

    // 最基本的读写
    @GetMapping("/redisString1")
    public String redisString1() {
        redisTemplate.opsForValue().set("jianshu", "szhouse");
        return redisTemplate.opsForValue().get("jianshu").toString();
    }

    // 覆写
    @GetMapping("/redisString2")
    public String redisString2() {
        // String
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(String.class));
        redisTemplate.opsForValue().set("jianshu", "szhouse");
        redisTemplate.opsForValue().set("jianshu", "2222222");
        String rtn = redisTemplate.opsForValue().get("jianshu").toString() + "\n";
        redisTemplate.opsForValue().set("jianshu", "999", 3L);
        rtn += redisTemplate.opsForValue().get("jianshu").toString();

        // Long
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        redisTemplate.opsForValue().set("jianshu", 1000);
        rtn += redisTemplate.opsForValue().get("jianshu").toString() + "\n";
        redisTemplate.opsForValue().set("jianshu", 9, 2);
        rtn += redisTemplate.opsForValue().get("jianshu").toString();
        return rtn;
    }

    // 运算和追加
    @GetMapping("/redisString3")
    public String redisString3() {
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        redisTemplate.opsForValue().set("jianshu", 999L);
        redisTemplate.opsForValue().increment("jianshu", 2L);
        String rtn = redisTemplate.opsForValue().get("jianshu").toString() + "\r\n";

        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(String.class));
        redisTemplate.opsForValue().set("jianshu", "abc");
        redisTemplate.opsForValue().append("jianshu", "123");
        rtn += redisTemplate.opsForValue().get("jianshu").toString();
        return rtn;
    }

    // 多值
    @GetMapping("/redisString4")
    public String redisString4() {
        Map<String, Integer> data = new HashMap<>();
        data.put("jianshu1", 1);
        data.put("jianshu2", 2);
        data.put("jianshu3", 3);
        redisTemplate.opsForValue().multiSet(data);

        String rtn = "";
        rtn += redisTemplate.opsForValue().get("jianshu2").toString() + "\n";

        List<String> keys = new ArrayList<>();
        keys.add("jianshu1");
        keys.add("jianshu2");
        keys.add("jianshu3");
        rtn += redisTemplate.opsForValue().multiGet(keys).toString();

        return rtn;
    }
}
