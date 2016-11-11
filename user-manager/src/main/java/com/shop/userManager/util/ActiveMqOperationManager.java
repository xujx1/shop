package com.shop.userManager.util;


import com.shop.userManager.bean.Order;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqOperationManager {

    private final static Logger logger = LoggerFactory.getLogger(ActiveMqOperationManager.class);

    @Autowired
    private ActiveMQTopic topic;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private SerializeManager serializeManager;

    @JmsListener(destination = "topic.shop.order", containerFactory = "topicListenerContainerFactory")
    private void listen(Order order) {
        
    }


    public void send(Order order) {
        jmsTemplate.convertAndSend(topic, serializeManager.serialize(order));
    }
}