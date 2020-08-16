package com.example.redisdemo.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shentan
 * @date 2020/8/16
 * @desc
 **/
@SpringBootTest
public class GeoOperationsDemoTest {

    @Autowired
    private RedisTemplate<String, String> template;

    @Resource(name = "redisTemplate")
    private GeoOperations<String, String> geo;

    /**
     * 将昌平、海淀区的经纬度添加到key为北京的集合中
     */
    @Test
    public void add() {
        Long add1 = geo.add("bj", new Point(117.17, 31.52), "cp");
        System.out.println(add1);
        Long add = geo.add("bj", new Point(128.13, 30.51), "hd");
        System.out.println(add);
    }

    /**
     * 获取北京集合里所有区的经纬度
     */
    @Test
    public void get() {
        List<Point> beijing = geo.position("bj", "cp", "hd");
        System.out.println(beijing);
    }

    /**
     * 获取北京集合里昌平区与海淀区的距离
     */
    @Test
    public void getJuli() {
        Distance distance = geo.distance("bj", "cp", "hd");
        System.out.println(distance);
    }

    /**
     * 获取指定范围内的元素
     */
    @Test
    public void getRadius() {
        List<Point> cp = geo.position("bj", "cp");
        if (CollectionUtils.isEmpty(cp)) {
            return;
        }
        System.out.println(cp);
        GeoResults<RedisGeoCommands.GeoLocation<String>> bj = geo.radius("bj", new Circle(new Point(0, 0), 10000000.0));
        System.out.println(bj);
    }

}