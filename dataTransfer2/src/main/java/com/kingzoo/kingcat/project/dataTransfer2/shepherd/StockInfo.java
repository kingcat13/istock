package com.kingzoo.kingcat.project.dataTransfer2.shepherd;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 股票信息 实体类
 * 
 * @author an hao 2016-3-31 上午10:38:58
 */
@Entity
public class StockInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long              id;                   // 主键 id

    private String            stockCode;            // 股票编码

    private String            stockName;            // 股票名称

    private Long              stockType;            // 股票类型（0：指数；1：普通）

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Long getStockType() {
        return stockType;
    }

    public void setStockType(Long stockType) {
        this.stockType = stockType;
    }

    @Override
    public boolean equals(Object obj) {
        StockInfo s = (StockInfo) obj;
        return this.getStockCode().equals(s.getStockCode());
    }
}
