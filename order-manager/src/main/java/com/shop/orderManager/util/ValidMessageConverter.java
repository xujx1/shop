package com.shop.orderManager.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.shop.orderManager.bean.Order;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component("validMessageConverter")
public class ValidMessageConverter extends MappingJackson2MessageConverter {

    @Override
    protected JavaType getJavaTypeForMessage(Message message) throws JMSException {
        return TypeFactory.defaultInstance().constructType(Order.class);
    }
}
