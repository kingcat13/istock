package com.kingzoo.kingcat.project.istockui.core.dataday.dao;


import com.kingzoo.kingcat.project.istockui.core.dataday.domain.StockDataLatest;

public interface IStockDataLatestDao extends Dao<StockDataLatest> {


    long countLatestData(String date);

    List<StockDataCount> countData();
	
}