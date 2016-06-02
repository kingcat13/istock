package com.kingzoo.kingcat.project.istock.manager.exchangedata.day.domain;

/**
 * Created by gonghongrui on 16/6/2.
 */
public class StockDataCount {

	private String code;
	private String dataDate;
	private int count;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
