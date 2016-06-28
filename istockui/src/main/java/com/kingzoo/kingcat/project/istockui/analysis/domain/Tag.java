package com.kingzoo.kingcat.project.istockui.analysis.domain;


import com.kingzoo.kingcat.project.istockui.stockdata.domain.StockDataDay;

/**
 * Created by gonghongrui on 15/8/6.
 */
public class Tag {

	private String name;

	private long date;

	private StockDataDay stockDataDay;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public StockDataDay getStockDataDay() {
		return stockDataDay;
	}

	public void setStockDataDay(StockDataDay stockDataDay) {
		this.stockDataDay = stockDataDay;
	}
}
