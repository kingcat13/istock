package com.kingzoo.kingcat.project.istockui.stockdata.dao;

import com.kingzoo.kingcat.project.istockui.stockdata.vo.StockDataCount;

import java.util.List;

/**
 * Created by gonghongrui on 16/6/7.
 */
public interface CustomStockDataDayRepository {

	long countLatestData(String date);

	List<StockDataCount> countData();
}
