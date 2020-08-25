package com.redis.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HyperLogLogOperations;
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
public class HyperLogLogDemoTest {

    @Qualifier("newRedisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String key = "hyper";

    @Test
    public void add() {
        Long add = redisTemplate.opsForHyperLogLog().add(key, "a", "b", "c");
        System.out.println(add);
    }

    @Test
    public void size() {
        Long size = redisTemplate.opsForHyperLogLog().size(key);
        System.out.println(size);
    }

}
