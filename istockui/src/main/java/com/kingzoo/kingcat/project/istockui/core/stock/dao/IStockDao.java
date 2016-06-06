package com.kingzoo.kingcat.project.istockui.core.stock.dao;


import com.kingzoo.kingcat.framework.mongo.repository.CustomMongoRepository;
import com.kingzoo.kingcat.project.istockui.core.stock.domain.Stock;

public interface IStockDao extends CustomMongoRepository<Stock, String> {
	
}