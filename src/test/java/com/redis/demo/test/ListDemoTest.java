package com.redis.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author shentan
 * @date 2020/8/24
 * @desc
 **/
@SpringBootTest
public class ListDemoTest {

    @Qualifier("newRedisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "newRedisTemplate")
    private ListOperations<String, String> list;

    private static final String key = "list";

    @Test
    public void push() {
        Long size = list.size(key);
        System.out.println(size);
        Long x = list.leftPush(key, "x");
        System.out.println(x);
    }

    @Test
    public void pushAll() {
        Long x = list.leftPushAll(key, "x", "y", "z");
        System.out.println(x);
        Long size = list.size(key);
        System.out.println(size);
    }

    @Test
    public void leftPop() {
        String s = list.leftPop(key);
        System.out.println(s);
        Long size = list.size(key);
        System.out.println(size);
    }

    @Test
    public void rightPush() {
        Long aLong = list.rightPush(key, "1");
        System.out.println(aLong);
        Long size = list.size(key);
        System.out.println(size);
    }

    @Test
    public void del() {
        list.getOperations().delete(key);
    }

    @Test
    public void blockPop() {
        String s = list.leftPop(key, 0, TimeUnit.SECONDS);
        System.out.println(s);
    }

}
