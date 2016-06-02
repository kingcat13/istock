package com.kingzoo.kingcat.project.istock.core.dataday.dao.mongo;

import com.kingzoo.kingcat.framework.mongo.dao.MongoBaseDao2;
import com.kingzoo.kingcat.project.istock.core.dataday.dao.IStockDataLatestDao;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataLatest;
import com.kingzoo.kingcat.project.istock.manager.exchangedata.day.domain.StockDataCount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gonghongrui on 16/3/5.
 */
@Repository("stockDataLatestDao")
public class StockDataLatestDao extends MongoBaseDao2<StockDataLatest> implements IStockDataLatestDao {


    @Override
    public Query buildQuery(StockDataLatest stockDataLatest) {
        Query whereSb = new Query();

        if (stockDataLatest != null) {
            if (!StringUtils.isEmpty(stockDataLatest.getCode())) {

                whereSb.addCriteria(Criteria.where("code").regex(stockDataLatest.getCode()));
            }

        }

        return whereSb;
    }


    public long countLatestData(String date){

        Query query = new Query();

        query.addCriteria(Criteria.where("dataDate").regex(date));



        return this.mongoOperations.count(query, this.clazz);

    }

    public List<StockDataCount> countData(){

        List<StockDataCount> results = new ArrayList<>();

        GroupByResults<StockDataCount> groupByResults = mongoOperations.group("stock_data_latest",
                GroupBy.key("dataDate")
                        .initialDocument("{ count: 0 }")
                        .reduceFunction("function(doc, prev) { prev.count += 1 }"),
                StockDataCount.class);

        Iterator<StockDataCount> i = groupByResults.iterator();

        while(i.hasNext()){
            StockDataCount item = i.next();
            results.add(item);
        }

        return results;

    }


}
