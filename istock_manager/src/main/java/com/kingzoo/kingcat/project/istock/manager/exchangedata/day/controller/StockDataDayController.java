package com.kingzoo.kingcat.project.istock.manager.exchangedata.day.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kingzoo.kingcat.framework.common.search.SortCondition;
import com.kingzoo.kingcat.framework.web.util.ResultList;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;
import com.kingzoo.kingcat.project.istock.manager.exchangedata.day.service.StockDataDayService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/exchangedata/day/history")
public class StockDataDayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockDataDayController.class);
	
	@Autowired
	private StockDataDayService stockDataDayService;

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()  ;
	
//	@RequestMapping(method = RequestMethod.POST)
//	public @ResponseBody StockDataDay add(@RequestBody StockDataDay stockDataDay){
//
//		stockDataDayService.add(stockDataDay);
//
//		return  stockDataDay;
//	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String addList(@RequestBody ArrayList<StockDataDay> stockDataDayList){

		LOGGER.info("add Day data start");
		stockDataDayService.add(stockDataDayList);
		LOGGER.info("add Day data end");
		return  "success";
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id){

        	stockDataDayService.delete(id);

        	ResponseEntity<String> responseResult = new ResponseEntity<String>(
                	"{success:true,message:'data already deleted'}", HttpStatus.OK);

        	return responseResult;
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public @ResponseBody
    ResultList update(
			@RequestBody StockDataDay stockDataDay, @PathVariable String id){

		List<StockDataDay> stockDataDayList = new ArrayList<StockDataDay>();;
		stockDataDayList.add(stockDataDay);
		
		stockDataDayService.meger(stockDataDayList);
		
		ResultList resultList = new ResultList(true,1,stockDataDayList);
		
		return  resultList;
	}

	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public @ResponseBody StockDataDay get(
			@PathVariable Long id
			) {
		
		StockDataDay stockDataDay = stockDataDayService.find(id);
	
		
		return stockDataDay;
	}

	@RequestMapping
	public @ResponseBody ResultList list(
			@RequestParam(required=false,value="start",defaultValue="0") Integer start,
			@RequestParam(required=false,value="limit",defaultValue="25") Integer limit,
			@RequestParam(required=false,value="sort") String sort,
			@RequestParam(required=false,value="condition") String condition
			) {
		
		LOGGER.debug(condition);
		LOGGER.debug("sort:"+sort);
		
		StockDataDay stockDataDayCondition = null;
		if(condition!=null){
			stockDataDayCondition = gson.fromJson(condition, StockDataDay.class);
		}
		
		List<SortCondition> sortConditions = null;
		if(sort!=null){
			sortConditions = gson.fromJson(sort, new TypeToken<List<SortCondition>>(){}.getType());
		}
		
		List<StockDataDay> stockDataDayList = stockDataDayService.find(start, limit, stockDataDayCondition, sortConditions);
		LOGGER.debug(stockDataDayList.toString());
		
		long total = stockDataDayService.count(stockDataDayCondition);
		LOGGER.debug(gson.toJson(stockDataDayList));
		
		ResultList resultList = new ResultList(true,total,stockDataDayList);
		
		return resultList;

	}
	
	@RequestMapping(params = "method=listAll")
	public @ResponseBody ResultList listAll(
			@RequestParam(required=false,value="sort") String sort,
			@RequestParam(required=false,value="condition") String condition
			) {
		
		LOGGER.info(condition);
		StockDataDay stockDataDayCondition = null;
		if(condition!=null){
			stockDataDayCondition = gson.fromJson(condition, StockDataDay.class);
		}
		
		
		List<SortCondition> sortConditions = null;
		if(sort!=null){
			sortConditions = gson.fromJson(sort, new TypeToken<List<SortCondition>>(){}.getType());
		}
	
		List<StockDataDay> stockDataDayList = stockDataDayService.findAll(stockDataDayCondition, sortConditions);
		LOGGER.info(stockDataDayList.toString());
		
		long total = stockDataDayService.count(stockDataDayCondition);
		
		ResultList resultList = new ResultList(true,total,stockDataDayList);
		
		return resultList;

	}


	@RequestMapping(value="/candlestickMapData/{code}",method = RequestMethod.GET)
	public @ResponseBody
	Map<String,Object> candlestickMapData(@PathVariable String code) throws ParseException {



		StockDataDay stockDataDayCondition = new StockDataDay();
		stockDataDayCondition.setCode(code);

		List<SortCondition> sortConditions = new ArrayList<SortCondition>();
		SortCondition sortCondition = new SortCondition();
		sortCondition.setProperty("dataDate");
		sortCondition.setDirectionASC();
		sortConditions.add(sortCondition);

		List<StockDataDay> stockDataDayList = stockDataDayService.findAll(stockDataDayCondition, sortConditions);
		List<Object[]> data = new ArrayList<Object[]>();
		for(StockDataDay dataDay : stockDataDayList){

			data.add(new Object[]{DateFormatUtils.ISO_DATE_FORMAT.parse(dataDay.getDataDate()), dataDay.getShoupanjia()/(double)100});
		}


		//取出需要分析的数据
		int analysisSize = 200;
		int startIndex = stockDataDayList.size() - analysisSize > 0 ? stockDataDayList.size()- analysisSize : 0;
		List<StockDataDay> analysisData = stockDataDayList.subList(startIndex, stockDataDayList.size()-1);

		HashMap<String,Object> result = new HashMap<>();
		result.put("success",true);
		result.put("total",0);
		result.put("items",data);

		//ResultList resultList = new ResultList(true, 0, data);

		return result;
	}
	
	
	/**
	 * @param stockDataDayService the stockDataDayService to set
	 */
	public void setStockDataDayService(StockDataDayService stockDataDayService) {
		this.stockDataDayService = stockDataDayService;
	}

	
}