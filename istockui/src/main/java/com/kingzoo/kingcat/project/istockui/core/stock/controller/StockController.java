package com.kingzoo.kingcat.project.istockui.core.stock.controller;

import com.kingzoo.kingcat.project.istockui.core.stock.domain.Stock;
import com.kingzoo.kingcat.project.istockui.core.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by gonghongrui on 16/6/6.
 */
@RestController
@RequestMapping(value = "/stock")
public class StockController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

	@Autowired
	StockService stockService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> add(
			@RequestBody Stock stock, UriComponentsBuilder ucBuilder) {

		if (!StringUtils.isEmpty(stock.getCode())) {
			LOGGER.info(String.format("A Stock with code %s already exist", stock.getCode()));
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		stockService.meger(stock);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/stock/{id}").buildAndExpand(stock.getCode()).toUri());

		ResponseEntity<Void> responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);

		return responseEntity;
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String id) {

		stockService.delete(id);

		ResponseEntity<String> responseResult = new ResponseEntity(
				"{success:true,message:'data already deleted'}", HttpStatus.OK);

		return responseResult;
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Stock> update(
			@RequestBody Stock stock,
			@PathVariable String id) {

		stock.setCode(id);
		stockService.meger(stock);

		ResponseEntity<Stock> responseEntity = new ResponseEntity<>(stock, HttpStatus.OK);

		return responseEntity;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Stock> get(@PathVariable String id) {
		Stock stock = stockService.get(id);
		ResponseEntity<Stock> responseEntity = new ResponseEntity<Stock>(stock, HttpStatus.OK);


		return responseEntity;
	}

	@RequestMapping(value = "/",method = RequestMethod.GET)
	public ResponseEntity<List<Stock>> getList() {

		List<Stock> stockList = stockService.findAll(null, null);

		ResponseEntity<List<Stock>> responseEntity = new ResponseEntity<>(stockList, HttpStatus.OK);


		return responseEntity;
	}
}
