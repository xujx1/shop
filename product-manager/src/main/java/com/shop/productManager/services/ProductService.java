package com.shop.productManager.services;

import com.shop.productManager.bean.Product;

import java.util.List;


public interface ProductService {
    List<Product> findAll();

    void save(Product product);

    Product findOne(Long id);

    List<Product> find(String name);
}
