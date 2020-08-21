package com.redis.demo.test;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author shentan
 * @date 2020/8/21
 * @desc
 **/
@SpringBootTest
public class HashOperationsDemoTest {

    @Autowired
    private RedisTemplate<String, String> template;

    @Resource(name = "redisTemplate")
    private HashOperations<String, Object, Object> hashOperations;

    private static final String key = "People:1";

    @Test
    public void set() {
        hashOperations.put(key, "age", 10);
        hashOperations.put(key, "age", 11);
        hashOperations.put(key, "name", "小李");
    }

    @Test
    public void get() {
        System.out.println(hashOperations.get(key, "age"));
        System.out.println(hashOperations.get(key, "name"));
        System.out.println(hashOperations.multiGet(key, Arrays.asList("age", "name")));
    }

    @Test
    public void setNx() {
        Boolean age = hashOperations.putIfAbsent(key, "agea", 12);
        System.out.println(age);
    }

    @Data
    static class People {
        int age;
        String name;
    }
}
