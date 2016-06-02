package com.kingzoo.kingcat.project.istock.core.dataday.dao.mongo;

import com.kingzoo.kingcat.framework.common.search.SortCondition;
import com.kingzoo.kingcat.project.istock.core.dataday.dao.IStockDataDayDao;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by gonghongrui on 16/3/5.
 */
@Repository("stockDataDayDao")
public class MongoStockDataDayDao implements IStockDataDayDao{


    @Autowired
    private MongoOperations mongoOperations;


    @Override
    public void persist(StockDataDay stockDataDay) {

        mongoOperations.save(stockDataDay);



    }

    @Override
    public void persist(List<StockDataDay> list) {
        for(StockDataDay stockDataDay:list) {
            mongoOperations.save(stockDataDay);
        }
    }

    @Override
    public void merge(StockDataDay stockDataDay) {
        mongoOperations.save(stockDataDay);
    }

    @Override
    public void merge(List<StockDataDay> list) {
        for(StockDataDay stockDataDay:list) {
            mongoOperations.save(stockDataDay);
        }
    }

    @Override
    public StockDataDay find(Long id) {
        if (id == null){
            return null;
        }
        return mongoOperations.findById(id, StockDataDay.class);


    }

    @Override
    public StockDataDay find(String id) {
        if (id == null){
            return null;
        }
        return mongoOperations.findById(id, StockDataDay.class);
    }

    @Override
    public void remove(Long id) {
        StockDataDay attached = find(id);
        if (attached != null){
            this.mongoOperations.remove(attached);
        }
    }

    @Override
    public void remove(String id) {
        StockDataDay attached = find(id);
        if (attached != null){
            this.mongoOperations.remove(attached);
        }
    }

    @Override
    public void remove(StockDataDay stockDataDay) {
        this.mongoOperations.remove(Query.query(where("name").is("1").where("a").is(2)), StockDataDay.class);
    }

    @Override
    public long count() {

        return mongoOperations.count(null,StockDataDay.class);
    }

    @Override
    public List<StockDataDay> find(int start, int limit) {
       Query query = new Query();
        query.skip(start).limit(limit);
        List<StockDataDay> result  = mongoOperations.find(query,StockDataDay.class);
        return result;
    }

    @Override
    public long count(StockDataDay stockDataDay) {

        Query query = this.buildQuery(stockDataDay);

        return this.mongoOperations.count(query, StockDataDay.class);
    }

    @Override
    public List<StockDataDay> find(int start, int limit, StockDataDay stockDataDay, List<SortCondition> sortConditions) {
        Query query = this.buildQuery(stockDataDay);
        query.skip(start).limit(limit);
        return this.mongoOperations.find(query,StockDataDay.class);
    }

    @Override
    public List<StockDataDay> findAll(StockDataDay stockDataDay, List<SortCondition> sortConditions) {

        Query query = this.buildQuery(stockDataDay);

        Sort sort = this.buildSort(sortConditions);
        query.with(sort);

        return this.mongoOperations.find(query, StockDataDay.class);
    }

    protected Query buildQuery(StockDataDay t) {
        Query whereSb = new Query();

        if (t != null) {
            if (!StringUtils.isEmpty(t.getCode())) {

                whereSb.addCriteria(Criteria.where("code").regex(t.getCode()));
            }

        }

        return whereSb;
    }

    protected Sort buildSort(List<SortCondition> sortConditions){

        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        for(SortCondition sc:sortConditions){
            Sort.Direction direction =  Sort.Direction.DESC;
            if(sc.getDirection().equals(SortCondition.SortDirection.ASC)){
                direction = Sort.Direction.DESC;
            }
            orders.add(new Sort.Order(direction, sc.getProperty()));
        }
        Sort sort = new Sort(orders);
        return sort;
    }
}
