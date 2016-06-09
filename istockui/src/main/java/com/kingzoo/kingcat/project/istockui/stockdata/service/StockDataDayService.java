package com.kingzoo.kingcat.project.istockui.stockdata.service;


import com.kingzoo.kingcat.project.istockui.stock.domain.Stock;
import com.kingzoo.kingcat.project.istockui.stock.service.StockService;
import com.kingzoo.kingcat.project.istockui.stockdata.dao.StockDataDayRepository;
import com.kingzoo.kingcat.project.istockui.stockdata.domain.StockDataDay;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("stockDataDayService")
public class StockDataDayService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockDataDay.class);
	
	@Autowired
	private StockDataDayRepository stockDataDayRepository;


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
			stockDataDayRepository.save(stockDataDay);
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
		stockDataDayRepository.delete(stockDataDay);
	}

	@Transactional
	public void delete(String id){
		stockDataDayRepository.delete(id);
	}
	
	@Transactional
	public void delete(List<StockDataDay> stockDataDays){
		for(StockDataDay stockDataDay: stockDataDays)
			delete(stockDataDay.getId());
	}
	
	@Transactional
	public void merge(StockDataDay stockDataDay){
		stockDataDayRepository.save(stockDataDay);


	}
	
	@Transactional
	public void meger(List<StockDataDay> stockDataDayList){
		for(StockDataDay stockDataDay:stockDataDayList){
			merge(stockDataDay);
		}
	}
	
	@Transactional(readOnly=true)
	public List<StockDataDay> findAll(StockDataDay stockDataDay, Sort sort){

		List<StockDataDay> stockDataDayList = stockDataDayRepository.find(stockDataDay, sort);

		return stockDataDayList;
	}
	
	@Transactional(readOnly=true)
	public Page<StockDataDay> find(int start,int limit,StockDataDay stockDataDay, Sort sort){

		int page = start % limit;

		Pageable pageRequest = new PageRequest(page, limit, sort);
		Page<StockDataDay> stockDataDayPage = stockDataDayRepository.find(stockDataDay, pageRequest);

		return stockDataDayPage;
	}
	
	@Transactional(readOnly=true)
	public long count(StockDataDay stockDataDay){
		long count = stockDataDayRepository.count(stockDataDay);
		return count;
	}
	
	@Transactional(readOnly=true)
	public StockDataDay find(String id){
		StockDataDay stockDataDay = stockDataDayRepository.findOne(id);

		return stockDataDay;
	}

}