package com.redis.demo.web;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author shentan
 * @date 2020/8/21
 * @desc
 **/
@RestController
public class DemoController {

    @Resource
    private RedisTemplate<String, String> template;

    private static final String pre = "key";

    /**
     * 简单版的redis锁
     *
     * @param id
     * @return
     */
    @GetMapping("/lock")
    public String lock(String id) {
        String name = Thread.currentThread().getName();
        String key = pre + id;
        boolean ret = template.opsForValue().setIfAbsent(key, "value");
        if (ret) {
            System.out.println("线程：" + name + " 获取锁成功，开始执行业务代码");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程：" + name + " 业务代码执行完毕");
            template.delete(key);
            System.out.println("线程：" + name + " 释放锁成功");
            return "成功";
        } else {
            System.err.println("线程：" + name + " 获取锁失败");
            return "失败";
        }
    }
}
