package com.kingzoo.kingcat.project.istock.manager.exchangedata.day.service;

import com.kingzoo.kingcat.framework.common.search.SortCondition;
import com.kingzoo.kingcat.project.istock.core.dataday.dao.IStockDataDayDao;
import com.kingzoo.kingcat.project.istock.core.dataday.dao.IStockDataLatestDao;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataLatest;
import com.kingzoo.kingcat.project.istock.core.stock.domain.Stock;
import com.kingzoo.kingcat.project.istock.core.stock.service.StockService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service("stockDataDayService")
public class StockDataDayService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockDataDay.class);
	
	@Autowired
	@Qualifier(value="stockDataDayDao")
	private IStockDataDayDao stockDataDayDao;

    @Autowired
    @Qualifier(value="stockDataLatestDao")
    private IStockDataLatestDao stockDataLatestDao;

    @Autowired
	@Qualifier(value="stockService")
	private StockService stockService;
	
	@Transactional
	public void add(StockDataDay stockDataDay){

        //先保存股票信息
		Stock stock = stockService.get(stockDataDay.getCode());
		if(stock==null){
			stock = new Stock();
			stock.setGoPublicDate(DateFormatUtils.ISO_DATE_FORMAT.format(new Date()));
		}
		stock.setCode(stockDataDay.getCode());
		stock.setName(stockDataDay.getName());
        stockService.meger(stock);

		//没成交，说明停牌了
		if(stockDataDay.getZongshou()!=null && stockDataDay.getZongshou()!=0) {
			stockDataDayDao.merge(stockDataDay);
		}

		StockDataLatest latest = new StockDataLatest();
        try {
            BeanUtils.copyProperties(latest,stockDataDay);
            latest.setId(latest.getCode());
            stockDataLatestDao.merge(latest);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (InvocationTargetException e) {
            LOGGER.error(e.getMessage(),e);
        }

    }
	
	@Transactional
	public void add(List<StockDataDay> stockDataDayList){
		for(StockDataDay stockDataDay:stockDataDayList){
			add(stockDataDay);
		}
	}
	
	@Transactional
	public void delete(StockDataDay stockDataDay){
		stockDataDayDao.remove(stockDataDay);
	}

	@Transactional
	public void delete(Long id){
		stockDataDayDao.remove(id);
	}
	
	@Transactional
	public void delete(List<StockDataDay> stockDataDays){
		for(StockDataDay stockDataDay: stockDataDays)
			delete(stockDataDay);
	}
	
	@Transactional
	public void merge(StockDataDay stockDataDay){
		stockDataDayDao.merge(stockDataDay);

        StockDataLatest latest = new StockDataLatest();
        try {
            BeanUtils.copyProperties(latest,stockDataDay);
            latest.setId(latest.getCode());
            stockDataLatestDao.merge(latest);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (InvocationTargetException e) {
            LOGGER.error(e.getMessage(),e);
        }
	}
	
	@Transactional
	public void meger(List<StockDataDay> stockDataDayList){
		for(StockDataDay stockDataDay:stockDataDayList){
			merge(stockDataDay);
		}
	}
	
	@Transactional(readOnly=true)
	public List<StockDataDay> findAll(StockDataDay stockDataDay, List<SortCondition> sortConditions){
		List<StockDataDay> stockDataDayList = stockDataDayDao.findAll(stockDataDay, sortConditions);

		return stockDataDayList;
	}
	
	@Transactional(readOnly=true)
	public List<StockDataDay> find(int start,int limit,StockDataDay stockDataDay, List<SortCondition> sortConditions){
		List<StockDataDay> stockDataDayList = stockDataDayDao.find(start, limit, stockDataDay, sortConditions);
		return stockDataDayList;
	}
	
	@Transactional(readOnly=true)
	public long count(StockDataDay stockDataDay){
		long count = stockDataDayDao.count(stockDataDay);
		return count;
	}
	
	@Transactional(readOnly=true)
	public StockDataDay find(Long id){
		StockDataDay stockDataDay = stockDataDayDao.find(id);

		return stockDataDay;
	}
	
	/**
	 * @return the IStockDataDayDao
	 */
	public IStockDataDayDao getStockDataDayDao() {
		return stockDataDayDao;
	}

	/**
	 * @param {model.name?uncap_first}Dao the IstockDataDayDao to set
	 */
	public void setStockDataDayDao(IStockDataDayDao stockDataDayDao) {
		this.stockDataDayDao = stockDataDayDao;
	}
}