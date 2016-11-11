package com.shop.orderManager.controllers;

import com.shop.orderManager.services.OrderService;
import com.shop.orderManager.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/select/{key}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo selectById(@PathVariable("key") String key) {
        return new ResponseVo.Builder().data(orderService.getCache(key)).build();
    }
}