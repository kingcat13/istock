package com.kingzoo.kingcat.project.istock.core.dataday.dao.mongo;

import com.kingzoo.kingcat.framework.mongo.dao.MongoBaseDao2;
import com.kingzoo.kingcat.project.istock.core.dataday.dao.IStockDataLatestDao;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataLatest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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


}
