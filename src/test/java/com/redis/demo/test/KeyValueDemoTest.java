package com.redis.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shentan
 * @date 2020/8/16
 * @desc
 **/
@SpringBootTest
public class KeyValueDemoTest {

    @Qualifier("newRedisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "newRedisTemplate")
    private ValueOperations<String, String> value;

    private static final String key = "key";

    private static final String Separator = "\n";

    @Test
    public void setIncr() {
        value.set("cnt", "1");
    }

    @Test
    public void incr() {
        value.setIfAbsent("cnt", "10000");
        Long cnt = value.increment("cnt");
        System.out.println(cnt);
        String cnt1 = value.get("cnt");
        System.out.println(cnt1);
    }

    @Test
    public void add() {
        for (int i = 1; i <= 10; i++) {
            String log = "this is log" + i;
            log += Separator;
            value.append("log", log);
        }
    }

    @Test
    public void set() {
        value.set("log", "123");
    }

    @Test
    public void get() {
        String log = value.get("log");
        System.out.println(log);
    }

    @Test
    public void del() {
        value.getOperations().delete("cnt");
    }

    @Test
    public void mSet() {
        Map<String, String> map = new HashMap<>();
        map.put("title", "给儿子的话");
        map.put("author", "st");
        map.put("price", "999");
        value.multiSet(map);
    }

    @Test
    public void mGet() {
        List<String> list = new ArrayList<>();
        list.add("title");
        list.add("author");
        list.add("price");
        List<String> values = value.multiGet(list);
        System.out.println(values);
    }

}