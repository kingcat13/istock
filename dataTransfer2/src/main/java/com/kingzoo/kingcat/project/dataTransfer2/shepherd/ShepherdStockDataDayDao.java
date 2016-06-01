package com.kingzoo.kingcat.project.dataTransfer2.shepherd;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by gonghongrui on 16/5/28.
 */
public interface ShepherdStockDataDayDao extends JpaRepository<ShepherdStockDataDay, Long>
{

        List<ShepherdStockDataDay> withStockCodeQuery(String stockCode);
        List<ShepherdStockDataDay> findAllByStockCodeAndStockTypeOrderByDateTimeAsc(String stockCode, Long stockType);
        }
