package com.kingzoo.kingcat.project.istock.manager.analysis.service;

import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;

import java.util.List;

/**
 * 判断初始趋势
 * Created by gonghongrui on 15/8/6.
 */
public class OriginalTrendFinder {

    private StockDataDay highest = null;
    private StockDataDay lowest = null;
    private StockDataDay higestBeforeLowest = null;
    private StockDataDay LowestBeforeHigest = null;

    //趋势类型
    private String trendType;

    public void find(List<StockDataDay> stockDataList){
        /*
        * 0.第一点时，最高价与最低价都是该价格
        * 1.当前价格比最低点之前的最高价格高9%，则上升趋势确立，之前为震荡趋势
        * 2.最低点之前无最高价，则上升趋势
        * 3.当前价格比最高点之前的最低价格低9%，则下降趋势确立，之前为震荡趋势
        * 4.最高点之前无最低价，则下降趋势
        *
        *
        * 震荡趋势
        */

        highest = stockDataList.get(0);
        lowest = stockDataList.get(0);

        for(int i = 1; i < stockDataList.size(); i++){

        }
    }

}
