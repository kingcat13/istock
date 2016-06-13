package com.kingzoo.kingcat.project.istockui.stockdata.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingzoo.kingcat.project.istockui.notification.service.NotificationService;
import com.kingzoo.kingcat.project.istockui.stock.domain.Stock;
import com.kingzoo.kingcat.project.istockui.stock.service.StockService;
import com.kingzoo.kingcat.project.istockui.stockdata.dao.StockDataDayRepository;
import com.kingzoo.kingcat.project.istockui.stockdata.dao.StockDataLatestRepository;
import com.kingzoo.kingcat.project.istockui.stockdata.domain.StockDataDay;
import com.kingzoo.kingcat.project.istockui.stockdata.domain.StockDataLatest;
import com.kingzoo.kingcat.project.istockui.stockdata.vo.StockDataCount;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

@Service("stockDataLatestService")
public class StockDataLatestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockDataLatestService.class);

	private static final Logger LATEST_DAY_DATA_LOGGER = LoggerFactory.getLogger("com.kingzoo.kingcat.project.istock.manager.exchangedata.day.latest");
	
	@Autowired
	private StockDataLatestRepository stockDataLatestRepository;

    @Autowired
    private StockDataDayRepository stockDataDayRepository;

	@Autowired
	@Qualifier(value="stockService")
	private StockService stockService;

	@Autowired
	private MongoTemplate mongoTemplate;


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
		stockDataLatestRepository.save(stockDataLatest);

        StockDataDay stockDataDay = new StockDataDay();

        BeanUtils.copyProperties(stockDataDay,stockDataLatest);

		//没成交，说明停牌了
        if(stockDataDay.getZongshou()!=null && stockDataDay.getZongshou()!=0) {

            stockDataDay.setId(stockDataDay.getCode()+"-"+stockDataDay.getDataDate());
            stockDataDayRepository.save(stockDataDay);
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
		stockDataLatestRepository.delete(stockDataLatest);
	}

	@Transactional
	public void delete(String id){
		stockDataLatestRepository.delete(id);
	}
	
	@Transactional
	public void delete(List<StockDataLatest> stockDataLatets){
		for(StockDataLatest stockDataLatest: stockDataLatets) {
			delete(stockDataLatest.getId());
		}
	}
	
	@Transactional
	public void meger(StockDataLatest stockDataLatest){
		stockDataLatestRepository.save(stockDataLatest);
	}
	
	@Transactional
	public void meger(List<StockDataLatest> stockDataLatestList){
		for(StockDataLatest stockDataLatest:stockDataLatestList){
			stockDataLatestRepository.save(stockDataLatest);
		}
		
	}
	
	@Transactional(readOnly=true)
	public List<StockDataLatest> findAll(StockDataLatest stockDataLatest, Sort sort){
		List<StockDataLatest> stockDataLatestList = stockDataLatestRepository.find(stockDataLatest, sort);

		return stockDataLatestList;
	}
	
	@Transactional(readOnly=true)
	public Page<StockDataLatest> find(int start, int limit, StockDataLatest stockDataLatest, Sort sort){


		if(sort==null){
			sort = new Sort(Sort.Direction.ASC, "code");
		}
		int page = start % limit;

		Pageable pageRequest = new PageRequest(page, limit, sort);



		Page<StockDataLatest> stockDataLatestList = stockDataLatestRepository.find(stockDataLatest, pageRequest);
		return stockDataLatestList;
	}
	
	@Transactional(readOnly=true)
	public long count(StockDataLatest stockDataLatest){
		long count = stockDataLatestRepository.count(stockDataLatest);
		return count;
	}
	
	@Transactional(readOnly=true)
	public StockDataLatest find(String id){

		StockDataLatest stockDataLatest = stockDataLatestRepository.findOne(id);

		return stockDataLatest;
	}


	/**
	 * 判断当天的股票处理数据是否已经下载完成
	 * @return
     */
	public void checkLatestData(){

		cleanData();

		String localIp = getLocalIp();


		/*
		 * 1.判断当天的是否有下载数据
		 * 2.如果没有下载数据, 则发推送消息, 提示管理员下载数据
		 */
		String date = DateFormatUtils.ISO_DATE_FORMAT.format(Calendar.getInstance());

		long count = stockDataLatestRepository.countLatestData(date);

		if(count==0){
			notificationService.sendToOne("kingcat", localIp+":"+date, date+" 的数据未下载, 请及时下载");
		}else{
			notificationService.sendToOne("kingcat", localIp+":"+date, date+" 已下载数据:" + count);
		}
	}

	/**
	 * 清理数据,只保留当前还在上市的股票的数据
	 * 当某只股票退市后,可能会出现其数据在存在最新数据中的情况
	 */
	private void cleanData(){
		List<StockDataCount> stockDataCountList = stockDataLatestRepository.countData();

		long max = 0;
		String maxDate = "";
		for(StockDataCount count : stockDataCountList){
			if(count.getCount() > max) {
				max = count.getCount() ;
				maxDate = count.getDataDate();
			}
		}

		Query query = new Query();
		query.addCriteria(Criteria.where("dataDate").ne(maxDate));
		mongoTemplate.remove(query, StockDataLatest.class);
	}

	private String getLocalIp(){

		String localIp = "--";
		try {
			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements())
			{
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();

				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements())
				{
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address)
					{
						if (!"127.0.0.1".equals(ip.getHostAddress())) {

							localIp = ip.getHostAddress()+"";
							return localIp;
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}

		return localIp;
	}


}