package com.shop.productManager.services.impl;

import com.shop.productManager.bean.Product;
import com.shop.productManager.dao.ProductDao;
import com.shop.productManager.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    public List<Product> findAll() {
        return productDao.list();
    }

    public void save(Product product) {
        Long id = productDao.count();
        product.setId(id);
        productDao.save(product);
    }

    public Product findOne(Long id) {
        return productDao.findOne(new Query(Criteria.where("_id").is(id)));
    }

    public List<Product> find(String name) {
        return productDao.list(new Query(Criteria.where("name").is(name)));
    }
}
