package com.shop.productManager.controllers;


import com.shop.productManager.bean.Product;
import com.shop.productManager.services.ProductService;
import com.shop.productManager.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/select-all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo selectAll() {
        return new ResponseVo.Builder().data(productService.findAll()).build();
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo save() {
        Product product = new Product();
        product.setName("许金鑫");
        productService.save(product);
        return new ResponseVo.Builder().data(product).build();
    }

    @RequestMapping(value = "/select-one/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo selectById(@PathVariable("id") Long id) {
        return new ResponseVo.Builder().data(productService.findOne(id)).build();
    }
}
