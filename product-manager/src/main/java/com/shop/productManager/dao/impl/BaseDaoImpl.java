package com.shop.productManager.dao.impl;

import com.shop.productManager.dao.BaseDao;
import com.shop.productManager.util.Pager;
import com.shop.productManager.util.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.util.List;


/**
 * 这个BaseDaoImpl实现了MongoDb大部分有用的方法
 */

abstract class BaseDaoImpl<T> implements BaseDao<T> {
    //获取class对象

    private Class<T> clazz;

    BaseDaoImpl() {
        if (null == this.clazz) {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            this.clazz = (Class<T>) type.getActualTypeArguments()[0];
        }
    }

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<T> list(Query query) {
        return mongoTemplate.find(query, clazz);
    }

    public List<T> list() {
        return mongoTemplate.findAll(clazz);
    }

    public T findOne(Query query) {
        return mongoTemplate.findOne(query, clazz);
    }

    public void update(Query query, Update update) {
        mongoTemplate.findAndModify(query, update, clazz);
    }

    public abstract void update(T entry);

    public abstract void save(T entity);

    public T load(int id) {
        return mongoTemplate.findById(id, clazz);
    }

    public T findById(String id, String collectionName) {
        return mongoTemplate.findById(id, clazz, collectionName);
    }

    public long count(Query query) {
        return mongoTemplate.count(query, clazz);
    }

    public long count() {
        return count(new Query(Criteria.where("").is("")));
    }

    public void delete(int id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), clazz);
    }

    public void deletes(int[] ids) {
        for (int id : ids) {
            delete(id);
        }
    }

    public void delete(T entry) {
        mongoTemplate.remove(entry);
    }

    public Pager<T> queryByPager() {
        return queryByPager(new Query(Criteria.where("").is("")));
    }

    public Pager<T> queryByPager(Query query) {
        // 获取页面的大小和 页面
        Integer pageSize = SystemContext.getPageSize();
        Integer pageNow = SystemContext.getPageNow();
        String order = SystemContext.getOrder();
        String sort = SystemContext.getSort();
        int count = Integer.parseInt(this.count(query) + "");
        Sort mySort;
        // 每页显示默认为10条数据
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        if (pageNow == null || pageNow < 0) {
            pageNow = 1;
        }
        if (order == null || "".equals(order.trim())) {
            order = "id";
        }
        int offset = (pageNow - 1) * pageSize;

        // 页面大小是多少
        query.limit(pageSize);
        // 从多少条开始
        query.skip(offset);

        //通过Sort这个 类可以完成排序的问题
        if (sort != null && sort.equals("desc")) {
            mySort = new Sort(Sort.Direction.DESC, order);
        } else {
            mySort = new Sort(Sort.Direction.ASC, order);
        }

        //设定排序
        query.with(mySort);
        List<T> dataList = this.mongoTemplate.find(query, clazz);
        // 设定数据
        Pager<T> pager = new Pager<T>();
        pager.setPageSize(pageSize);
        pager.setPageNow(pageNow);
        //设定页面的数
        pager.setPageCount((count - 1) / pageSize + 1);
        pager.setRowCount(count);
        pager.setData(dataList);
        return pager;
    }
}
