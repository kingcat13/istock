package com.kingzoo.kingcat.project.istockui.core.stock.service;


import com.kingzoo.kingcat.project.istock.core.stock.dao.IStockDao;
import com.kingzoo.kingcat.project.istock.core.stock.domain.Stock;
import com.kingzoo.kingcat.framework.common.search.SortCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("stockService")
public class StockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);
	
	@Autowired
	@Qualifier(value="stockDao")
	private IStockDao stockDao;

	
	@Transactional
	public void delete(Stock stock){
		List<Stock> stockList = this.findAll(stock, null);
        for(Stock tempStock:stockList){
            stockDao.remove(tempStock.getCode());
        }
	}

	@Transactional
	@CacheEvict(value = "default", key = "id")
	public void delete(String id){

		stockDao.remove(id);
	}
	
	@Transactional
	public void delete(List<String> idList){
		for(String id: idList) {
            delete(id);
        }
	}
	
	@Transactional
	@CachePut(value = "default", key = "#stock.getCode()")
	public void meger(Stock stock){
		stockDao.merge(stock);
	}
	
	@Transactional
	public void meger(List<Stock> stockList){
		for(Stock stock:stockList){
			stockDao.merge(stock);
		}
		
	}
	
	@Transactional(readOnly=true)
	public List<Stock> findAll(Stock stock, List<SortCondition> sortConditions){
		List<Stock> stockList = stockDao.findAll(stock, sortConditions);

		return stockList;
	}
	
	@Transactional(readOnly=true)
	public List<Stock> find(int start, int limit, Stock stock, List<SortCondition> sortConditions){
		List<Stock> stockList = stockDao.find(start, limit, stock, sortConditions);
		return stockList;
	}
	
	@Transactional(readOnly=true)
	public long count(Stock stock){
		long count = stockDao.count(stock);
		return count;
	}
	
	@Transactional(readOnly=true)
	@Cacheable(value = "default")
	public Stock get(String id){
		Stock stock = stockDao.find(id);

		return stock;
	}


	/**
	 * @param {model.name?uncap_first}Dao the IstockDao to set
	 */
	public void setStockDao(IStockDao stockDao) {
		this.stockDao = stockDao;
	}
}