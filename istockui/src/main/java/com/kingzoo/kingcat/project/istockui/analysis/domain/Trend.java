package com.kingzoo.kingcat.project.istockui.analysis.domain;



/**
 * 某种趋势
 * Created by gonghongrui on 15/8/7.
 */
public class Trend {

    public static final String TRENDTYPE_EMPTY = "EMPTY";
    public static final String TRENDTYPE_UP = "UP";
    public static final String TRENDTYPE_DOWN = "DOWN";
    public static final String TRENDTYPE_SWIFT = "SWIFT";

    //趋势类型 上升、下跌、震荡、无趋势
    public String trendType;

    //趋势开始时间
    public String startTime;

    //趋势结束时间
    public String endTime;

    //趋势最高值
    public Ohlc high;

    //趋势最低值
    public Ohlc low;



}
