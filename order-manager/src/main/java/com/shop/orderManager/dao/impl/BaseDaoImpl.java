package com.shop.orderManager.dao.impl;

import com.shop.orderManager.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BaseDaoImpl implements BaseDao {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void setCache(String key, String value, int time, TimeUnit timeUnit) {
        deleteCache(key);
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public String getCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }

    public void updateCache(String key, String value, int time, TimeUnit timeUnit) {
        deleteCache(key);
        setCache(key, value, time, timeUnit);
    }

    public int countCache() {
        Set<String> keys = redisTemplate.opsForValue().getOperations().keys("*");
        return keys.size();
    }

    public String getKey(String key) {
        Set<String> keys = redisTemplate.opsForValue().getOperations().keys(key + "_*");
        return key + "_" + keys.size();
    }
}
