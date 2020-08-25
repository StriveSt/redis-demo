package com.redis.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author shentan
 * @date 2020/8/24
 * @desc
 **/
@SpringBootTest
public class ZSetDemoTest {

    @Qualifier("newRedisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "newRedisTemplate")
    private ZSetOperations<String, String> zSet;

    private static final String key = "zSet";

    @Test
    public void add() {
        Boolean st = zSet.add(key, "st", 100);
        System.out.println(st);
    }

    @Test
    public void get() {
        Long aLong = zSet.zCard(key);
        System.out.println(aLong);
    }

    @Test
    public void getScore() {
        Double st = zSet.score(key, "st");
        System.out.println(st);
        zSet.incrementScore(key, "st", 1);
        st = zSet.score(key, "st");
        System.out.println(st);
    }

    @Test
    public void rank() {
        Long st = zSet.rank(key, "st");
        System.out.println(st);
    }

    @Test
    public void auto() {
        feed("黄建宏", 30);
        feed("黄坚强", 3000);
        feed("黄晓明", 5000);
        feed("张三", 2700);
        feed("李四", 1500);
        Set<String> set = hit("黄建", 10);
        System.out.println(set);
    }

    private Set<String> hit(String prefix, int i) {
        String k = key + prefix;
        return zSet.reverseRange(k, 0, i - 1);
    }

    private void feed(String name, double weight) {
        String[] split = name.split("");
        StringBuilder val = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            val.append(split[i]);
            String k = "auto3:" + val;
            zSet.incrementScore(k, val.toString(), weight);
        }
    }

}
