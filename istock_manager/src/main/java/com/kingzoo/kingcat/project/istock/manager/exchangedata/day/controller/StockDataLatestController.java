package com.kingzoo.kingcat.project.istock.manager.exchangedata.day.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kingzoo.kingcat.framework.common.search.SortCondition;
import com.kingzoo.kingcat.framework.web.util.ResultList;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataLatest;
import com.kingzoo.kingcat.project.istock.manager.exchangedata.day.service.StockDataLatestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/exchangedataday/latest")
public class StockDataLatestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockDataLatestController.class);
	
	@Autowired
	private StockDataLatestService stockDataLatestService;

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()  ;
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody StockDataLatest add(@RequestBody StockDataLatest stockDataLatest){
		
		stockDataLatestService.add(stockDataLatest);

		return  stockDataLatest;
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id){

        	stockDataLatestService.delete(id);

        	ResponseEntity<String> responseResult = new ResponseEntity<String>(
                	"{success:true,message:'data already deleted'}", HttpStatus.OK);

        	return responseResult;
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public @ResponseBody
    ResultList update(
			@RequestBody StockDataLatest stockDataLatest, @PathVariable String id){

		List<StockDataLatest> stockDataLatestList = new ArrayList<StockDataLatest>();;
		stockDataLatestList.add(stockDataLatest);
		
		stockDataLatestService.meger(stockDataLatestList);
		
		ResultList resultList = new ResultList(true,1,stockDataLatestList);
		
		return  resultList;
	}

	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public @ResponseBody StockDataLatest get(
			@PathVariable Long id
			) {
		
		StockDataLatest stockDataLatest = stockDataLatestService.find(id);
	
		
		return stockDataLatest;
	}

	@RequestMapping
	public @ResponseBody ResultList list(
			@RequestParam(required=false, value="start", defaultValue="0") Integer start,
			@RequestParam(required=false, value="limit", defaultValue="25") Integer limit,
			@RequestParam(required=false, value="sort") String sort,
			@RequestParam(required=false, value="condition") String condition
			) throws UnsupportedEncodingException {

        LOGGER.debug(condition);
		LOGGER.debug("sort:" + sort);
		
		StockDataLatest stockDataLatestCondition = null;
		if(condition!=null){
			stockDataLatestCondition = gson.fromJson(condition, StockDataLatest.class);
		}
		
		List<SortCondition> sortConditions = null;
		if(sort!=null){
			sortConditions = gson.fromJson(sort, new TypeToken<List<SortCondition>>(){}.getType());
		}
		
		List<StockDataLatest> stockDataLatestList = stockDataLatestService.find(start, limit, stockDataLatestCondition, sortConditions);
		LOGGER.debug(stockDataLatestList.toString());
		
		long total = stockDataLatestService.count(stockDataLatestCondition);
		LOGGER.debug(gson.toJson(stockDataLatestList));
		
		ResultList resultList = new ResultList(true,total,stockDataLatestList);
		
		return resultList;

	}
	
	@RequestMapping(params = "method=listAll")
	public @ResponseBody ResultList listAll(
			@RequestParam(required=false,value="sort") String sort,
			@RequestParam(required=false,value="condition") String condition
			) {
		
		LOGGER.debug(condition);
		StockDataLatest stockDataLatestCondition = null;
		if(condition!=null){
			stockDataLatestCondition = gson.fromJson(condition, StockDataLatest.class);
		}
		
		
		List<SortCondition> sortConditions = null;
		if(sort!=null){
			sortConditions = gson.fromJson(sort, new TypeToken<List<SortCondition>>(){}.getType());
		}
	
		List<StockDataLatest> stockDataLatestList = stockDataLatestService.findAll(stockDataLatestCondition, sortConditions);
		LOGGER.debug(stockDataLatestList.toString());
		
		long total = stockDataLatestService.count(stockDataLatestCondition);
		
		ResultList resultList = new ResultList(true,total,stockDataLatestList);
		
		return resultList;

	}
	
	
	
	/**
	 * @param stockDataLatestService the stockDataLatestService to set
	 */
	public void setStockDataLatestService(StockDataLatestService stockDataLatestService) {
		this.stockDataLatestService = stockDataLatestService;
	}

	
}