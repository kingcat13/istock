package com.kingzoo.kingcat.project.istockui.stockdata.dao;

import com.kingzoo.kingcat.framework.mongo.repository.CustomMongoRepository;
import com.kingzoo.kingcat.project.istockui.stockdata.domain.StockDataLatest;

/**
 * Created by gonghongrui on 16/6/7.
 */
public interface StockDataLatestRepository extends CustomMongoRepository<StockDataLatest, String>, CustomStockDataDayRepository{

}
