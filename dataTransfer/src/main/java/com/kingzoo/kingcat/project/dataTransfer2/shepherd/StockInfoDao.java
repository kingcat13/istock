package com.kingzoo.kingcat.project.dataTransfer2.shepherd;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by gonghongrui on 16/5/28.
 */
public interface StockInfoDao extends JpaRepository<StockInfo, Long> {

	List<StockInfo> findAllByStockType(Long stockType);

}
