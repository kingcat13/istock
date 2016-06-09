package com.kingzoo.kingcat.project.istockui.stock.service;


import com.kingzoo.kingcat.project.istockui.stock.dao.StockRepository;
import com.kingzoo.kingcat.project.istockui.stock.domain.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("stockService")
public class StockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

	@Autowired
	private StockRepository stockRepository;


	@Transactional
	public void delete(Stock stock){
		List<Stock> stockList = this.findAll(stock, null);
		for(Stock tempStock:stockList){
			stockRepository.delete(tempStock.getCode());
		}
	}

	@Transactional
	@CacheEvict(value = "default", key = "id")
	public void delete(String id){

		stockRepository.delete(id);
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
		stockRepository.save(stock);
	}

	@Transactional
	public void meger(List<Stock> stockList){
		for(Stock stock:stockList){
			stockRepository.save(stock);
		}

	}

	@Transactional(readOnly=true)
	public List<Stock> findAll(Stock stock, Sort sort){

		List<Stock> stockList = stockRepository.find(stock, sort);

		return stockList;
	}

	@Transactional(readOnly=true)
	public Page<Stock> find(int start, int limit, Stock stock, Sort sort){

		int page = start % limit;

		Pageable pageRequest = new PageRequest(page, limit, sort);


		Page<Stock> stockPage = stockRepository.find(stock, pageRequest);
		return stockPage;
	}

	@Transactional(readOnly=true)
	public long count(Stock stock){
		long count = stockRepository.count();
		return count;
	}

	@Transactional(readOnly=true)
	@Cacheable(value = "default")
	public Stock get(String id){
		Stock stock = stockRepository.findOne(id);

		return stock;
	}
}