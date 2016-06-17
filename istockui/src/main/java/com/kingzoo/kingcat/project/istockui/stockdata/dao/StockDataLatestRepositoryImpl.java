package com.kingzoo.kingcat.project.istockui.stockdata.dao;


import com.kingzoo.kingcat.project.istockui.stockdata.domain.StockDataLatest;
import com.kingzoo.kingcat.project.istockui.stockdata.vo.StockDataCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gonghongrui on 16/3/5.
 */
public class StockDataLatestRepositoryImpl implements CustomStockDataLatestRepository {


    @Autowired
    MongoOperations mongoOperations;


    public long countLatestData(String date){

        Query query = new Query();

        query.addCriteria(Criteria.where("dataDate").regex(date));



        return this.mongoOperations.count(query, StockDataLatest.class);

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
