package com.kingzoo.kingcat.project.dataTransfer2.istock;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;


@Document(collection = "stock_data_day")
public class StockDataDay {

	/**code+date*/
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;


    @Column
    private String code;
    

    @Column
    private String dataDate;
    
    
    @Column
    private String name;

	/**单位:分*/
    @Column
    private Integer zuoshou;

	/**单位:分*/
    @Column
    private Integer kaipanjia;

	/**单位:分*/
    @Column
    private Integer shoupanjia;

	/**单位:分*/
    @Column
    private Integer zuigaojia;

	/**单位:分*/
    @Column
    private Integer zuidijia;

	/**单位:万*/
    @Column
    private Long zongjine;

	/**单位:分*/
    @Column
    private Integer zhangdiezhi;

	/**单位:0.01%*/
    @Column
    private Integer zhangdiefu;

	/**单位:分*/
    @Column
    private Integer junjia;

	/**单位:手*/
    @Column
    private Integer zongshou;

	/**单位:0.01%*/
    @Column
    private Integer weibi;
    
    
    @Column
    private Integer weicha;

	/**单位:0.01%*/
    @Column
    private Integer zhangsu;

	/**单位:0.01%*/
    @Column
    private Integer huanshou;

	/**单位:股*/
    @Column
    private Long zongguben;

	/**单位:0.01*/
    @Column
    private Integer liangbi;

	/**单位:0.01%*/
    @Column
    private Integer zhenfu;


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

	public String getCode() {
		return code;
	}

	public Integer getZuoshou() {
		return zuoshou;
	}

	public void setZuoshou(Integer zuoshou) {
		this.zuoshou = zuoshou;
	}

	public Integer getKaipanjia() {
		return kaipanjia;
	}

	public void setKaipanjia(Integer kaipanjia) {
		this.kaipanjia = kaipanjia;
	}

	public Integer getShoupanjia() {
		return shoupanjia;
	}

	public void setShoupanjia(Integer shoupanjia) {
		this.shoupanjia = shoupanjia;
	}

	public Integer getZuigaojia() {
		return zuigaojia;
	}

	public void setZuigaojia(Integer zuigaojia) {
		this.zuigaojia = zuigaojia;
	}

	public Integer getZuidijia() {
		return zuidijia;
	}

	public void setZuidijia(Integer zuidijia) {
		this.zuidijia = zuidijia;
	}

	public Long getZongjine() {
		return zongjine;
	}

	public void setZongjine(long zongjine) {
		this.zongjine = zongjine;
	}

	public Integer getZhangdiezhi() {
		return zhangdiezhi;
	}

	public void setZhangdiezhi(Integer zhangdiezhi) {
		this.zhangdiezhi = zhangdiezhi;
	}

	public Integer getZhangdiefu() {
		return zhangdiefu;
	}

	public void setZhangdiefu(Integer zhangdiefu) {
		this.zhangdiefu = zhangdiefu;
	}

	public Integer getJunjia() {
		return junjia;
	}

	public void setJunjia(Integer junjia) {
		this.junjia = junjia;
	}

	public Integer getZongshou() {
		return zongshou;
	}

	public void setZongshou(Integer zongshou) {
		this.zongshou = zongshou;
	}

	public Integer getWeibi() {
		return weibi;
	}

	public void setWeibi(Integer weibi) {
		this.weibi = weibi;
	}

	public Integer getWeicha() {
		return weicha;
	}

	public void setWeicha(Integer weicha) {
		this.weicha = weicha;
	}

	public Integer getZhangsu() {
		return zhangsu;
	}

	public void setZhangsu(Integer zhangsu) {
		this.zhangsu = zhangsu;
	}

	public Integer getHuanshou() {
		return huanshou;
	}

	public void setHuanshou(Integer huanshou) {
		this.huanshou = huanshou;
	}

	public Long getZongguben() {
		return zongguben;
	}

	public void setZongguben(Long zongguben) {
		this.zongguben = zongguben;
	}

	public Integer getLiangbi() {
		return liangbi;
	}

	public void setLiangbi(Integer liangbi) {
		this.liangbi = liangbi;
	}

	public Integer getZhenfu() {
		return zhenfu;
	}

	public void setZhenfu(Integer zhenfu) {
		this.zhenfu = zhenfu;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}