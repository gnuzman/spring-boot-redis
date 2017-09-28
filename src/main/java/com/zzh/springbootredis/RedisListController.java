package com.zzh.springbootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RedisListController {
    @Autowired
    private RedisTemplate redisTemplate;

    // 基本存取
    @GetMapping("/redisList1")
    public List redisList1() {
        redisTemplate.delete("jianshuList");
        redisTemplate.opsForList().rightPush("jianshuList", "szhouse1_right");
        redisTemplate.opsForList().rightPush("jianshuList", "szhouse2_right");
        redisTemplate.opsForList().rightPush("jianshuList", "szhouse3_right");

        StringBuilder builder = new StringBuilder();
        redisTemplate.opsForList().range("jianshuList", 0, -1).forEach(value->{
            System.out.println(value);
        });

        redisTemplate.opsForList().leftPush("jianshuList", "szhouse1_left");
        redisTemplate.opsForList().leftPush("jianshuList", "szhouse2_left");
        redisTemplate.opsForList().leftPush("jianshuList", "szhouse3_left");

        redisTemplate.opsForList().range("jianshuList", 0, -1).forEach(value->{
            System.out.println(value);
        });

        return redisTemplate.opsForList().range("jianshuList", 0, -1);
    }

    // 批量保存
    @GetMapping("/redisList2")
    public List redisList2() {
        redisTemplate.delete("jianshuList2");
        List<String> listDataLeft = new ArrayList<>();
        listDataLeft.add("111_left");
        listDataLeft.add("222_left");
        listDataLeft.add("333_left");
        redisTemplate.opsForList().leftPushAll("jianshuList2", listDataLeft);

        List<String> listDataRight = new ArrayList<>();
        listDataRight.add("111_right");
        listDataRight.add("222_right");
        listDataRight.add("333_right");
        redisTemplate.opsForList().rightPushAll("jianshuList2", listDataRight);

        return redisTemplate.opsForList().range("jianshuList2", 0, -1);
    }

    // 删除指定位置的元素
    @GetMapping("/redisList3")
    public List redisList3() {
        redisTemplate.delete("jianshuList3");
        redisTemplate.opsForList().leftPush("jianshuList3", "szhouse1_left");
        redisTemplate.opsForList().leftPush("jianshuList3", "szhouse2_left");
        redisTemplate.opsForList().leftPush("jianshuList3", "szhouse3_left");

        // 指定位置删除
        redisTemplate.opsForList().trim("jianshuList3", 1, -1);
        return redisTemplate.opsForList().range("jianshuList3", 0, -1);
    }

    // 删除值
    @GetMapping("/redisList4")
    public List redisList4() {
        redisTemplate.delete("jianshuList4");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse1");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse2");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse3");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse1");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse2");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse3");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse1");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse2");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse3");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse1");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse2");
        redisTemplate.opsForList().rightPush("jianshuList4", "szhouse3");

        // 删除所有的 szhouse1
        redisTemplate.opsForList().remove("jianshuList4", 0, "szhouse1");
        redisTemplate.opsForList().range("jianshuList4", 0, -1).forEach(value->{
            System.out.println(value);
        });
        System.out.println("------------------------------------------------------------");
        // 从左往右删除1个szhouse2
        redisTemplate.opsForList().remove("jianshuList4", 1, "szhouse2");
        redisTemplate.opsForList().range("jianshuList4", 0, -1).forEach(value->{
            System.out.println(value);
        });

        System.out.println("------------------------------------------------------------");
        // 从右往左删除1个szhouse3
        redisTemplate.opsForList().remove("jianshuList4", -1, "szhouse3");
        redisTemplate.opsForList().range("jianshuList4", 0, -1).forEach(value->{
            System.out.println(value);
        });

        return redisTemplate.opsForList().range("jianshuList4", 0, -1);
    }
}
