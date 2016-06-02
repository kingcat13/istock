package com.kingzoo.kingcat.project.istockui.core.stock.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Stock {

    //@Version
    private Integer version;


    @Id
    private String code;
    
    
    
    private String name;
    
    
    
    private String goPublicDate;
    
    
    
    private String stockExchange;

    
    private String lastDataDay;
    
    
    public String getCode(){
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
    
    public String getName(){
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    public String getGoPublicDate(){
		return goPublicDate;
	}
	public void setGoPublicDate(String goPublicDate) {
		this.goPublicDate = goPublicDate;
	}

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public String getLastDataDay() {
        return lastDataDay;
    }

    public void setLastDataDay(String lastDataDay) {
        this.lastDataDay = lastDataDay;
    }
	
	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}