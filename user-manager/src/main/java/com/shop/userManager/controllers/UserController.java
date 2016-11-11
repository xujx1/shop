package com.shop.userManager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.shop.userManager.bean.Order;
import com.shop.userManager.bean.Product;
import com.shop.userManager.bean.User;
import com.shop.userManager.enums.ObjectMapperSingleton;
import com.shop.userManager.services.UserService;
import com.shop.userManager.util.ActiveMqOperationManager;
import com.shop.userManager.vo.ResponseVo;
import com.sun.tools.internal.ws.processor.model.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ActiveMqOperationManager activeMqOperationManager;

    private static final ObjectMapper objectMapper = ObjectMapperSingleton.INSTANCE.getInstance();

    @RequestMapping(value = "/select-all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo selectAll() {
        return new ResponseVo.Builder().data(userService.selectAll()).build();
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo selectById(@PathVariable("id") Integer id) {
        return new ResponseVo.Builder().data(userService.selectUserById(id)).build();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public void update() {
        User user = userService.selectUserById(1);
        user.setUsername("测试");
        userService.updateUserInfoById(user);
    }

    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    @ResponseBody
    public void buy() throws IOException {
        User user = userService.selectUserById(1);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/shop/product-manager/product/select-one/1", String.class);
        if (null != responseEntity) {
            String body = responseEntity.getBody();
            if (StringUtils.isNotEmpty(body)) {
                ResponseVo responseVo = objectMapper.readValue(body, ResponseVo.class);
                if (null != responseVo) {
                    Product product = objectMapper.readValue(objectMapper.writeValueAsString(responseVo.getData()), Product.class);
                    if (null != product) {
                        Order order = new Order(user, product);
                        activeMqOperationManager.send(order);
                    }
                }
            }
        }
    }
}
