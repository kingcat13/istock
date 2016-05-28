package com.shepherd.data.stockDataDay.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 股票日数据 实体
 * 
 * @author an hao 2016-4-1 下午3:47:29
 */
public class ShepherdStockDataDay implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long              id;                   // 主键

    private String            stockCode;            // 股票编码

    private Long              stockType;            // 股票类型（0：指数；1：普通）

    private String            dateTime;             // 日期

    private String            openPrice;            // 开盘价

    private String            highPrice;            // 最高价

    private String            lowPrice;             // 最低价

    private String            closePrice;           // 收盘价

    private String            changeAmount;         // 涨跌额

    private String            changeRange;          // 涨跌幅(%)

    private String            dealVolume;           // 成交量(股)

    private String            dealAmount;           // 成交金额(元)

    private String            swingRange;           // 振幅(%) （指数类型没有振幅）

    private String            turnoverRate;         // 换手率(%)（指数类型没有换手率）

    private String            fiveAverage;          // 五日均价

    private String            tenAverage;           // 十日均价

    private String            changeRangeFive;      // 五日均线涨跌幅(%)

    private String            changeRangeTen;       // 十日均线涨跌幅(%)

    public String getChangeRangeTen() {
        return changeRangeTen;
    }

    public void setChangeRangeTen(String changeRangeTen) {
        this.changeRangeTen = changeRangeTen;
    }

    public String getChangeRangeFive() {
        return changeRangeFive;
    }

    public void setChangeRangeFive(String changeRangeFive) {
        this.changeRangeFive = changeRangeFive;
    }

    public String getTenAverage() {
        return tenAverage;
    }

    public void setTenAverage(String tenAverage) {
        this.tenAverage = tenAverage;
    }

    public String getFiveAverage() {
        return fiveAverage;
    }

    public void setFiveAverage(String fiveAverage) {
        this.fiveAverage = fiveAverage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(String changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getChangeRange() {
        return changeRange;
    }

    public void setChangeRange(String changeRange) {
        this.changeRange = changeRange;
    }

    public String getDealVolume() {
        return dealVolume;
    }

    public void setDealVolume(String dealVolume) {
        this.dealVolume = dealVolume;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }



    public String getSwingRange() {
        return swingRange;
    }

    public void setSwingRange(String swingRange) {
        this.swingRange = swingRange;
    }

    public String getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(String turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public Long getStockType() {
        return stockType;
    }

    public void setStockType(Long stockType) {
        this.stockType = stockType;
    }
}
