package com.redis.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author shentan
 * @date 2020/8/24
 * @desc
 **/
@SpringBootTest
public class SetDemoTest {

    @Qualifier("newRedisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "newRedisTemplate")
    private SetOperations<String, String> set;

    private static final String key = "set";

    @Test
    public void add() {
        Long add = set.add(key, "4", "5", "6", "7");
        System.out.println(add);
    }

    @Test
    public void remove() {
        Long remove = set.remove(key,"3");
        System.out.println(remove);
    }

    @Test
    public void list() {
        Set<String> members = set.members(key);
        System.out.println(members);
    }

    @Test
    public void move() {
        set.move(key, "2", "set2");
        System.out.println(set.members(key));
        System.out.println(set.members("set2"));
    }

    @Test
    public void isExist() {
        Boolean member = set.isMember(key, "1");
        System.out.println(member);
    }

    @Test
    public void randomGet() {
        String s = set.randomMember(key);
        System.out.println(s);
        List<String> strings = set.randomMembers(key, 2);
        System.out.println(strings);
    }
}
