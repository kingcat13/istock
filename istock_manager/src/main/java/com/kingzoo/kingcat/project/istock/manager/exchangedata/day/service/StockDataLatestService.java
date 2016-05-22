package com.kingzoo.kingcat.project.istock.manager.exchangedata.day.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingzoo.kingcat.framework.common.search.SortCondition;
import com.kingzoo.kingcat.project.istock.core.dataday.dao.IStockDataDayDao;
import com.kingzoo.kingcat.project.istock.core.dataday.dao.IStockDataLatestDao;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataLatest;
import com.kingzoo.kingcat.project.istock.core.stock.domain.Stock;
import com.kingzoo.kingcat.project.istock.core.stock.service.StockService;
import com.kingzoo.kingcat.project.istock.notification.service.NotificationService;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("stockDataLatestService")
public class StockDataLatestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockDataLatestService.class);
	private static final Logger LATEST_DAY_DATA_LOGGER = LoggerFactory.getLogger("com.kingzoo.kingcat.project.istock.manager.exchangedata.day.latest");
	
	@Autowired
	@Qualifier(value="stockDataLatestDao")
	private IStockDataLatestDao stockDataLatestDao;


    @Autowired
    @Qualifier(value="stockDataDayDao")
    private IStockDataDayDao stockDataDayDao;

	@Autowired
	@Qualifier(value="stockService")
	private StockService stockService;


	@Autowired
	private NotificationService notificationService;


	ObjectMapper mapper = new ObjectMapper();

	
	@Transactional
	public void add(StockDataLatest stockDataLatest){
		/*
		 * 1.记录日交易数据到日志文件
		 * 2.保存股票信息的(判断是否是新上市股票)
		 * 3.保存数据至最新数据表
		 * 4.保存数据至历史数据表
		 */

		//记录最新数据到日志里
		try {
			LATEST_DAY_DATA_LOGGER.info(mapper.writeValueAsString(stockDataLatest));
		} catch (JsonProcessingException e) {
			LOGGER.error("", e);
		}

		//先保存股票信息
		Stock stock = stockService.get(stockDataLatest.getCode());
		if(stock==null){
			stock = new Stock();
			stock.setGoPublicDate(DateFormatUtils.ISO_DATE_FORMAT.format(new Date()));
		}
		stock.setCode(stockDataLatest.getCode());
		stock.setName(stockDataLatest.getName());
		stockService.meger(stock);

        //stockDataLatest.setId(IDGenerator.get());
		stockDataLatest.setId(stockDataLatest.getCode());
		stockDataLatestDao.merge(stockDataLatest);

        StockDataDay stockDataDay = new StockDataDay();
		try {

            BeanUtils.copyProperties(stockDataDay,stockDataLatest);
            //没成交，说明停牌了
            if(stockDataDay.getZongshou()!=null && stockDataDay.getZongshou()!=0) {

                stockDataDay.setId(stockDataDay.getCode()+"-"+stockDataDay.getDataDate());
                stockDataDayDao.merge(stockDataDay);
            }


		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}
	
	@Transactional
	public void add(List<StockDataLatest> stockDataLatestList){
		for(StockDataLatest stockDataLatest:stockDataLatestList){
			this.add(stockDataLatest);
		}
		
	}
	
	@Transactional
	public void delete(StockDataLatest stockDataLatest){
		stockDataLatestDao.remove(stockDataLatest);
	}

	@Transactional
	public void delete(Long id){
		stockDataLatestDao.remove(id);
	}
	
	@Transactional
	public void delete(List<StockDataLatest> stockDataLatests){
		for(StockDataLatest stockDataLatest: stockDataLatests)
			delete(stockDataLatest);
	}
	
	@Transactional
	public void meger(StockDataLatest stockDataLatest){
		stockDataLatestDao.merge(stockDataLatest);
	}
	
	@Transactional
	public void meger(List<StockDataLatest> stockDataLatestList){
		for(StockDataLatest stockDataLatest:stockDataLatestList){
			stockDataLatestDao.merge(stockDataLatest);
		}
		
	}
	
	@Transactional(readOnly=true)
	public List<StockDataLatest> findAll(StockDataLatest stockDataLatest, List<SortCondition> sortConditions){
		List<StockDataLatest> stockDataLatestList = stockDataLatestDao.findAll(stockDataLatest, sortConditions);

		return stockDataLatestList;
	}
	
	@Transactional(readOnly=true)
	public List<StockDataLatest> find(int start,int limit,StockDataLatest stockDataLatest, List<SortCondition> sortConditions){
		if(sortConditions==null){
			sortConditions = new ArrayList<>();
			SortCondition sc = new SortCondition();
			sc.setDirectionASC();
			sc.setProperty("code");
			sortConditions.add(sc);

		}
		List<StockDataLatest> stockDataLatestList = stockDataLatestDao.find(start, limit, stockDataLatest, sortConditions);
		return stockDataLatestList;
	}
	
	@Transactional(readOnly=true)
	public long count(StockDataLatest stockDataLatest){
		long count = stockDataLatestDao.count(stockDataLatest);
		return count;
	}
	
	@Transactional(readOnly=true)
	public StockDataLatest find(Long id){
		StockDataLatest stockDataLatest = stockDataLatestDao.find(id);

		return stockDataLatest;
	}

	/**
	 * 判断当天的股票处理数据是否已经下载完成
	 * @return
     */
	public void checkLatestData(){

		/*
		 * 1.判断当天的是否有下载数据
		 * 2.如果没有下载数据, 则发推送消息, 提示管理员下载数据
		 */
		String date = DateFormatUtils.ISO_DATE_FORMAT.format(Calendar.getInstance());

		long count = stockDataLatestDao.countLatestData(date);

		if(count==0){
			notificationService.sendToOne("kingcat", date+" 数据未下载", date+" 的数据未下载, 请及时下载");
		}else{
			notificationService.sendToOne("kingcat", date+" 已下载数据", date+" 已下载数据:" + count);
		}

	}
}