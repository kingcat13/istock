package com.kingzoo.kingcat.project.istock.core.stock.dao.mongo;

import com.kingzoo.kingcat.framework.mongo.dao.MongoBaseDao2;
import com.kingzoo.kingcat.project.istock.core.stock.dao.IStockDao;
import com.kingzoo.kingcat.project.istock.core.stock.domain.Stock;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by gonghongrui on 16/3/5.
 */
@Repository("stockDao")
public class MongoStockDao extends MongoBaseDao2<Stock> implements IStockDao {
    @Override
    public Query buildQuery(Stock stock) {
        return null;
    }
}
