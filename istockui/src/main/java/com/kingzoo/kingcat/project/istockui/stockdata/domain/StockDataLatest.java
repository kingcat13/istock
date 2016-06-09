package com.kingzoo.kingcat.project.istockui.stockdata.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "stock_data_latest")
public class StockDataLatest {
    
    
    
    @Id
    private String id;
	
    private Integer version;

    private String code;
    
    private String dataDate;

    private String name;

    private String zuoshou;
    
    private String kaipanjia;
    
    private String shoupanjia;
    
    private String zuigaojia;
    
    private String zuidijia;
    
    private String zongjine;
    
    private String zhangdiezhi;
    
    private String zhangdiefu;
    
    private String junjia;

    private String zongshou;

    private String weibi;

    private String weicha;
    
    private String zhangsu;

    private String huanshou;

    private String zongguben;

    private String liangbi;

    private String zhenfu;
    
    
    public String getCode(){
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
    
    public String getDataDate(){
		return dataDate;
	}
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
    
    public String getName(){
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    public String getZuoshou(){
		return zuoshou;
	}
	public void setZuoshou(String zuoshou) {
		this.zuoshou = zuoshou;
	}
    
    public String getKaipanjia(){
		return kaipanjia;
	}
	public void setKaipanjia(String kaipanjia) {
		this.kaipanjia = kaipanjia;
	}
    
    public String getShoupanjia(){
		return shoupanjia;
	}
	public void setShoupanjia(String shoupanjia) {
		this.shoupanjia = shoupanjia;
	}
    
    public String getZuigaojia(){
		return zuigaojia;
	}
	public void setZuigaojia(String zuigaojia) {
		this.zuigaojia = zuigaojia;
	}
    
    public String getZuidijia(){
		return zuidijia;
	}
	public void setZuidijia(String zuidijia) {
		this.zuidijia = zuidijia;
	}
    
    public String getZongjine(){
		return zongjine;
	}
	public void setZongjine(String zongjine) {
		this.zongjine = zongjine;
	}
    
    public String getZhangdiezhi(){
		return zhangdiezhi;
	}
	public void setZhangdiezhi(String zhangdiezhi) {
		this.zhangdiezhi = zhangdiezhi;
	}
    
    public String getZhangdiefu(){
		return zhangdiefu;
	}
	public void setZhangdiefu(String zhangdiefu) {
		this.zhangdiefu = zhangdiefu;
	}
    
    public String getJunjia(){
		return junjia;
	}
	public void setJunjia(String junjia) {
		this.junjia = junjia;
	}
    
    public String getZongshou(){
		return zongshou;
	}
	public void setZongshou(String zongshou) {
		this.zongshou = zongshou;
	}
    
    public String getWeibi(){
		return weibi;
	}
	public void setWeibi(String weibi) {
		this.weibi = weibi;
	}
    
    public String getWeicha(){
		return weicha;
	}
	public void setWeicha(String weicha) {
		this.weicha = weicha;
	}
    
    public String getZhangsu(){
		return zhangsu;
	}
	public void setZhangsu(String zhangsu) {
		this.zhangsu = zhangsu;
	}
    
    public String getHuanshou(){
		return huanshou;
	}
	public void setHuanshou(String huanshou) {
		this.huanshou = huanshou;
	}
    
    public String getZongguben(){
		return zongguben;
	}
	public void setZongguben(String zongguben) {
		this.zongguben = zongguben;
	}
    
    public String getLiangbi(){
		return liangbi;
	}
	public void setLiangbi(String liangbi) {
		this.liangbi = liangbi;
	}
    
    public String getZhenfu(){
		return zhenfu;
	}
	public void setZhenfu(String zhenfu) {
		this.zhenfu = zhenfu;
	}
    
    
    /**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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