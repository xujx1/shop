package com.shop.orderManager.util;


import com.shop.orderManager.Environment;
import com.shop.orderManager.bean.Order;
import com.shop.orderManager.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class ActiveMqOperationManager {

    private final static Logger logger = LoggerFactory.getLogger(ActiveMqOperationManager.class);

    @Autowired
    private OrderService orderService;

    @JmsListener(destination = "topic.shop.order", containerFactory = "topicListenerContainerFactory")
    private void listen(Order order) {
        logger.info(order.toString());
        Integer userId = order.getUserId();
        Integer productId = order.getProductId();
        if (null != order && null != userId && null != productId) {
            String key = userId + "_" + productId;
            orderService.setCache(key, order, Environment.TIME, Environment.TIME_UNIT);
        }
    }
}