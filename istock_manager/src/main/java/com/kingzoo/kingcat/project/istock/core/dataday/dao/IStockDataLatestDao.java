package com.kingzoo.kingcat.project.istock.core.dataday.dao;

import com.kingzoo.kingcat.framework.common.dao.Dao;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataLatest;
import com.kingzoo.kingcat.project.istock.manager.exchangedata.day.domain.StockDataCount;

import java.util.List;

public interface IStockDataLatestDao extends Dao<StockDataLatest> {


    long countLatestData(String date);

    List<StockDataCount> countData();
	
}