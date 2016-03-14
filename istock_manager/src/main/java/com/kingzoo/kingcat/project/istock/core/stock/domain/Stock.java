package com.kingzoo.kingcat.project.istock.core.stock.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;


@Entity
@Table
public class Stock {

    //@Version
    @Column(name = "version")
    private Integer version;


    @Id
    @Column
    @org.springframework.data.annotation.Id
    private String code;
    
    
    @Column
    private String name;
    
    
    @Column
    private String goPublicDate;
    
    
    @Column
    private String stockExchange;

    @Column
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