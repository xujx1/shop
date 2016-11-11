package com.shop.orderManager.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.orderManager.enums.ObjectMapperSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SerializeManager {

    private final static Logger logger = LoggerFactory.getLogger(SerializeManager.class);
    private ObjectMapper objectMapper = ObjectMapperSingleton.INSTANCE.getInstance();

    /**
     * 反序列化
     * json转换为对象
     */
    public <T> T deSerialize(String json, Class<T> classType) {
        try {
            return objectMapper.readValue(json, classType);
        } catch (IOException e) {
            logger.error("Json to Object Error,{}", e);
            return null;
        }
    }

    /**
     * 序列化
     * 直接转为json
     */
    public String serialize(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error("Object to Json Error,{}", e);
            return "";
        }
    }
}
