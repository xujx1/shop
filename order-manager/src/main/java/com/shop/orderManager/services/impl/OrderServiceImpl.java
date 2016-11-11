package com.shop.orderManager.services.impl;

import com.shop.orderManager.Environment;
import com.shop.orderManager.bean.Order;
import com.shop.orderManager.dao.OrderDao;
import com.shop.orderManager.services.OrderService;
import com.shop.orderManager.util.SerializeManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SerializeManager serializeManager;

    public void setCache(String key, Order order, int time, TimeUnit timeUnit) {
        order.setId(orderDao.countCache());
        String value = serializeManager.serialize(order);
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
            key = orderDao.getKey(key);
            time = 0 == time ? Environment.TIME : time;
            timeUnit = null == timeUnit ? Environment.TIME_UNIT : timeUnit;
            orderDao.setCache(key, value, time, timeUnit);
        }
    }

    public Order getCache(String key) {
        Order order = null;
        if (StringUtils.isNotEmpty(key)) {
            String json = orderDao.getCache(key);
            if (StringUtils.isNotEmpty(json)) {
                order = serializeManager.deSerialize(json, Order.class);
            }
        }
        return order;
    }

    public void deleteCache(String key) {
        if (StringUtils.isNotEmpty(key)) {
            orderDao.deleteCache(key);
        }
    }

    public void updateCache(String key, Order order, int time, TimeUnit timeUnit) {
        order.setId(getCache(key).getId());
        String value = serializeManager.serialize(order);
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
            time = 0 == time ? Environment.TIME : time;
            timeUnit = null == timeUnit ? Environment.TIME_UNIT : timeUnit;
            orderDao.updateCache(key, value, time, timeUnit);
        }
    }
}
