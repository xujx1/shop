package com.shop.orderManager.dao;


import java.util.concurrent.TimeUnit;

public interface BaseDao {

    void setCache(String key, String value, int time, TimeUnit timeUnit);

    String getCache(String key);

    void deleteCache(String key);

    void updateCache(String key, String value, int time, TimeUnit timeUnit);

    int countCache();

    String getKey(String key);
}
