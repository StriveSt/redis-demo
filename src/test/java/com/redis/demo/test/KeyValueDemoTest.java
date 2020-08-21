package com.redis.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shentan
 * @date 2020/8/16
 * @desc
 **/
@SpringBootTest
public class KeyValueDemoTest {

    @Autowired
    private RedisTemplate<String, String> template;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> value;

    private static final String key = "key";

    @Test
    public void add() {
        value.set("123","2");
    }

    @Test
    public void lock() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 50; i++) {
            executor.execute(() -> {
                boolean ret = value.setIfAbsent(key, "value");
                if (ret) {
                    System.out.println("获取锁成功，开始执行业务代码");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("业务代码执行完毕");
                    value.getOperations().delete(key);
                } else {
                    System.out.println("获取锁失败");
                }
            });
        }
    }

}