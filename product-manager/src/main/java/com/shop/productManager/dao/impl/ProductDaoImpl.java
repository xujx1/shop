package com.shop.productManager.dao.impl;


import com.shop.productManager.bean.Product;
import com.shop.productManager.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void update(Product entry) {
        Query query = new Query(Criteria.where("_id").is(entry.getId()));
        Update update = new Update();
        update.set("name", entry.getName());
        mongoTemplate.updateFirst(query, update, Product.class);
    }

    public void save(Product entity) {
        mongoTemplate.save(entity);
    }
}
