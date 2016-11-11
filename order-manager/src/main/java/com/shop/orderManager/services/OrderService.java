package com.shop.orderManager.services;

import com.shop.orderManager.bean.Order;

import java.util.concurrent.TimeUnit;

public interface OrderService {

    void setCache(String key, Order order, int time, TimeUnit timeUnit);

    Order getCache(String key);

    void deleteCache(String key);

    void updateCache(String key, Order order, int time, TimeUnit timeUnit);
}
