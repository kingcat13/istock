package com.kingzoo.kingcat.project.dataGetter.daydatagetter.eastmoney;

/**
 * Created by gonghongrui on 15/3/2.
 */
public class StockData {

    private String[] propteries;

    public StockData(String[] propteries) {

        this.propteries = propteries;

    }

    public String getCode() {
        return propteries[1];
    }

    public String getName() {
        return propteries[2];
    }

    public Integer getZuoshou() {
        return getFenFromYuan(propteries[3]);
    }

    public Integer getKaipanjia() {
        return getFenFromYuan(propteries[4]);
    }

    public Integer getShoupanjia() {
        return getFenFromYuan(propteries[5]);
    }

    public Integer getZuigaojia() {
        return getFenFromYuan(propteries[6]);
    }

    public Integer getZuidijia() {
        return getFenFromYuan(propteries[7]);
    }

    public Long getZongjine() {
        return Long.parseLong(propteries[8]);
    }

    public Integer getZongshou() {
        return Integer.parseInt(propteries[9]);
    }

    public Integer getZhangdiezhi() {
        return getFenFromYuan(propteries[10]);
    }

    public Integer getZhangdiefu() {
        return parsePercentToPerTenhousandInteger(propteries[11]);
    }

    public Integer getJunjia() {
        return getFenFromYuan(propteries[12]);
    }

    public Integer getZhenfu() {
        return parsePercentToPerTenhousandInteger(propteries[13]);
    }

    public Integer getWeibi() {
        return parsePercentToPerTenhousandInteger(propteries[14]);
    }

    public Integer getWeicha() {
        return Integer.parseInt(propteries[15]);
    }

    public Integer getZhangsu() {
        return parsePercentToPerTenhousandInteger(propteries[21]);
    }

    public Integer getLiangbi() {
        return getFenFromYuan(propteries[22]);
    }

    public Integer getHuanshou() {
        return parsePercentToPerTenhousandInteger(propteries[23]);
    }

    public String getDataDate() {
        return propteries[28].split(" ")[0];
    }

    public Long getZongguben() {
        return Long.parseLong(propteries[30]);
    }


    /**
     * 解析百分比至万分之一的整数,
     * 比如 1% ---> 100
     * @return
     */
    private Integer parsePercentToPerTenhousandInteger(String value){
        value = value.replace("%","");
        Double percentValue = Double.parseDouble(value);
        percentValue = percentValue*100;
        return percentValue.intValue();
    }


    /**
     * 把"元"转换成"分"
     * 比如 1元--->100分
     * @param value
     * @return
     */
    private Integer getFenFromYuan(String value){
        Double percentValue = Double.parseDouble(value);
        Long result = Math.round(percentValue*100);

        return result.intValue();
    }




}
