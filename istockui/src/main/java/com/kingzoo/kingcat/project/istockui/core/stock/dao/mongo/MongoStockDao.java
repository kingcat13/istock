package com.kingzoo.kingcat.project.istockui.core.stock.dao.mongo;


import com.kingzoo.kingcat.project.istockui.core.stock.dao.IStockDao;
import com.kingzoo.kingcat.project.istockui.core.stock.domain.Stock;
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
