package com.kingzoo.kingcat.project.istockui.stockdata.dao;


import com.kingzoo.kingcat.framework.mongo.repository.CustomMongoRepository;
import com.kingzoo.kingcat.project.istockui.stockdata.domain.StockDataDay;

public interface StockDataDayRepository extends CustomMongoRepository<StockDataDay, String>{

}