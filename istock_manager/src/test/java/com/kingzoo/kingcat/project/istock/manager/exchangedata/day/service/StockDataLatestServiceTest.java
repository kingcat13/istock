package com.kingzoo.kingcat.project.istock.manager.exchangedata.day.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by gonghongrui on 16/4/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext*.xml")
public class StockDataLatestServiceTest {

    @Resource
    private StockDataLatestService stockDataLatestService;

    @Test
    public void checkLatestData() {
        stockDataLatestService.checkLatestData();

    }
}
