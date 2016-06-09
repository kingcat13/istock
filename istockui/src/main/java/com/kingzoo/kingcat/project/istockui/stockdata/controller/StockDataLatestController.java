package com.kingzoo.kingcat.project.istockui.stockdata.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kingzoo.kingcat.project.istockui.stock.domain.Stock;
import com.kingzoo.kingcat.project.istockui.stockdata.domain.StockDataLatest;
import com.kingzoo.kingcat.project.istockui.stockdata.service.StockDataLatestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/stockdataday/latest")
public class StockDataLatestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockDataLatestController.class);
	
	@Autowired
	private StockDataLatestService stockDataLatestService;

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()  ;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<StockDataLatest> add(@RequestBody StockDataLatest stockDataLatest, UriComponentsBuilder ucBuilder){
		
		stockDataLatestService.add(stockDataLatest);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/stockdataday/latest/{id}").buildAndExpand(stockDataLatest.getId()).toUri());

		ResponseEntity<StockDataLatest> responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);

		return responseEntity;

	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String id){

        	stockDataLatestService.delete(id);

        	ResponseEntity<String> responseResult = new ResponseEntity<String>(
                	"{success:true,message:'data already deleted'}", HttpStatus.OK);

        	return responseResult;
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<StockDataLatest> update(
			@RequestBody StockDataLatest stockDataLatest, @PathVariable String id){

		stockDataLatest.setId(id);
		stockDataLatestService.meger(stockDataLatest);

		ResponseEntity<StockDataLatest> responseEntity = new ResponseEntity(stockDataLatest, HttpStatus.OK);
		
		return  responseEntity;
	}

	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ResponseEntity<StockDataLatest> get(
			@PathVariable String id
			) {
		
		StockDataLatest stockDataLatest = stockDataLatestService.find(id);

		HttpStatus httpStatus = HttpStatus.OK;
		ResponseEntity<StockDataLatest> responseEntity;

		if(stockDataLatest==null){
			httpStatus = HttpStatus.NOT_FOUND;
		}

		responseEntity = new ResponseEntity(stockDataLatest, httpStatus);

		
		return responseEntity;
	}

	@RequestMapping(value = "/")
	public ResponseEntity<Page<StockDataLatest>> list(
			@RequestParam(required=false, value="start", defaultValue="0") Integer start,
			@RequestParam(required=false, value="limit", defaultValue="25") Integer limit,
			@RequestParam(required=false, value="sort.order") String orderBy,
			@RequestParam(required=false, value="sort.direction", defaultValue = "ASC") String direction,
			@RequestParam(required=false, value="condition") String condition
			) throws UnsupportedEncodingException {

        LOGGER.debug(condition);
		
		StockDataLatest stockDataLatestCondition = null;
		if(condition!=null){
			stockDataLatestCondition = gson.fromJson(condition, StockDataLatest.class);
		}
		Sort sort = null;
		if(!StringUtils.isEmpty(orderBy)){
			Sort.Order order = new Sort.Order(Sort.Direction.fromStringOrNull(direction), orderBy);
			sort = new Sort(order);
		}

		
		Page<StockDataLatest> stockDataLatestPage = stockDataLatestService.find(start, limit, stockDataLatestCondition, sort);
		LOGGER.debug(stockDataLatestPage.toString());


		ResponseEntity<Page<StockDataLatest>> responseEntity = new ResponseEntity<>(stockDataLatestPage, HttpStatus.OK);
		
		return responseEntity;

	}

}