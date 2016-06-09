package com.kingzoo.kingcat.project.istockui.stock.dao;


import com.kingzoo.kingcat.framework.mongo.repository.CustomMongoRepository;
import com.kingzoo.kingcat.project.istockui.stock.domain.Stock;

public interface StockRepository extends CustomMongoRepository<Stock, String> {
	
}