package com.kingzoo.kingcat.project.dataGetter.daydatagetter.eastmoney;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingzoo.kingcat.project.istock.core.dataday.dao.IStockDataDayDao;
import com.kingzoo.kingcat.project.istock.core.dataday.dao.IStockDataLatestDao;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataLatest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gonghongrui on 14/11/16.
 */
@Service(value = "eastMoneyDayDataGetter")
public class EastMoneyDayDataGetter  {

    private static final Logger LOGGER = LoggerFactory.getLogger(EastMoneyDayDataGetter.class);

    private static String eastMoneySHDataUrl = "http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/index.aspx" +
            "?type=s&sortType=C&sortRule=-1&pageSize=20&page=%d&jsName=quote_123&style=10";

    private static String eastMoneySZDataUrl = "http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/index.aspx" +
            "?type=s&sortType=C&sortRule=-1&pageSize=20&page=%d&jsName=quote_123&style=20";

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    @Qualifier(value = "stockDataDayDao")
    private IStockDataDayDao stockDataDayDao;

    @Autowired
    @Qualifier(value = "stockDataLatestDao")
    private IStockDataLatestDao stockDataLatestDao;


    public void downloadDayDataToLocal() {
        int totalPage = 1000;
        PageData pageData = null;
        List<StockDataDay> dayDataList = null;
        for (int currentPage = 1; currentPage <= totalPage; currentPage++) {

            String url = String.format(eastMoneySHDataUrl, currentPage);
            pageData = this.getPageData(url);
            totalPage = pageData.getPages();

            dayDataList = this.parsePageDataToDayDataList(pageData);
            meger(dayDataList);
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                LOGGER.error("", e);
            }
        }

        totalPage = 1000;
        for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
            String url = String.format(eastMoneySZDataUrl, currentPage);
            pageData = this.getPageData(url);
            totalPage = pageData.getPages();

            dayDataList = this.parsePageDataToDayDataList(pageData);
            meger(dayDataList);
            try {
                Thread.sleep(new Random().nextInt(1500));
            } catch (InterruptedException e) {
                LOGGER.error("", e);
            }
        }
    }

    private PageData getPageData(String url){
        LOGGER.info("start load stock exchange data from "+url);
        PageData pageData = null;

        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String json = EntityUtils.toString(entity, "UTF-8");
                json = json.replace("var quote_123=", "");
                json = json.replace("rank", "\"rank\"");
                json = json.replace("pages", "\"pages\"");
                pageData = mapper.readValue(json, PageData.class);
            }
        } catch (IOException e) {
            LOGGER.error("", e);
        }
        LOGGER.info("end load sz stock exchange data.");
        return pageData;
    }





    private List<StockDataDay> parsePageDataToDayDataList(PageData page) {
        List<StockDataDay> result = new ArrayList<StockDataDay>();
        for (String strData : page.getRank()) {
            result.add(parseStringToDayData(strData));
        }
        return result;
    }

    private StockDataDay parseStringToDayData(String strData) {
        /*
         *6007481,
        600748, 证券代码
        上实发展,
        8.79, 昨收
        8.80, 开盘价
        9.62, 最新价
        9.67, 最高价
        8.72,最低价
        67228, 总金额 （单位万）
        707132, 总手（单位 手）
        0.83, 涨跌值
        9.44%, 涨跌幅
        9.51, 均价
        10.81%, 振幅
        -56.29%, 委比（%）
        -23152, 委差
        855,
        457724,
        249407,
        0,
        1,
        -0.41%, 涨速（%）
        3.94, 量比
        6.53%, 换手(%)
        10.76,
        001145|002451|003495|003594|003596|003632|003701|003707|5009,
        9.60, 买一价
        9.62, 卖一价
        2014-11-14 15:03:03,
        0,
        1083370880,总股本，
        9522829994,
        5.4
         */
        String[] propteries = strData.split(",");
        StockData stockData = new StockData(propteries);
        StockDataDay dayData = new StockDataDay();

        dayData.setCode(stockData.getCode() + "");
        dayData.setName(stockData.getName());
        dayData.setZuoshou(stockData.getZuoshou());
        dayData.setKaipanjia(stockData.getKaipanjia());
        dayData.setShoupanjia(stockData.getShoupanjia());
        dayData.setZuigaojia(stockData.getZuigaojia());
        dayData.setZuidijia(stockData.getZuidijia());
        dayData.setZongjine(stockData.getZongjine());
        dayData.setZongshou(stockData.getZongshou());
        dayData.setZhangdiezhi(stockData.getZhangdiezhi());
        dayData.setZhangdiefu(stockData.getZhangdiefu());
        dayData.setJunjia(stockData.getJunjia());
        dayData.setZhenfu(stockData.getZhenfu());
        dayData.setWeibi(stockData.getWeibi());
        dayData.setWeicha(stockData.getWeicha());
        dayData.setZhangsu(stockData.getZhangsu());
        dayData.setLiangbi(stockData.getLiangbi());
        dayData.setHuanshou(stockData.getHuanshou());
        dayData.setDataDate(stockData.getDataDate());
        dayData.setZongguben(stockData.getZongguben());

        dayData.setId(dayData.getCode() + "-" + dayData.getDataDate());

        return dayData;
    }

    @Transactional
    public void merge(StockDataDay stockDataDay) {
        stockDataDayDao.merge(stockDataDay);

        StockDataLatest latest = new StockDataLatest();
        try {
            BeanUtils.copyProperties(latest, stockDataDay);
            latest.setId(latest.getCode());
            stockDataLatestDao.merge(latest);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Transactional
    public void meger(List<StockDataDay> stockDataDayList) {
        for (StockDataDay stockDataDay : stockDataDayList) {
            merge(stockDataDay);
        }
    }

    public static void main(String[] args) {

        String url = String.format(eastMoneySHDataUrl, 1);
        System.out.println(new EastMoneyDayDataGetter().getPageData(url));

        ClassPathXmlApplicationContext xml = new ClassPathXmlApplicationContext("spring/*.xml");
        xml.start();
        EastMoneyDayDataGetter dayDataGetter = (EastMoneyDayDataGetter) xml.getBean("dayDataGetter");

        dayDataGetter.downloadDayDataToLocal();
        xml.stop();

    }


}
