package com.kingzoo.kingcat.project.dataTransfer2;

import com.kingzoo.kingcat.project.dataTransfer2.istock.StockDataDay;
import com.kingzoo.kingcat.project.dataTransfer2.istock.StockDataDayDao;
import com.kingzoo.kingcat.project.dataTransfer2.istock.TransferLog;
import com.kingzoo.kingcat.project.dataTransfer2.istock.TransferLogRepository;
import com.kingzoo.kingcat.project.dataTransfer2.shepherd.ShepherdStockDataDay;
import com.kingzoo.kingcat.project.dataTransfer2.shepherd.ShepherdStockDataDayDao;
import com.kingzoo.kingcat.project.dataTransfer2.shepherd.StockInfo;
import com.kingzoo.kingcat.project.dataTransfer2.shepherd.StockInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@SpringBootApplication()
public class DataTransfer2Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataTransfer2Application.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@RequestMapping(value ="/")
	String index(){
		return "你好";
	}

	@RequestMapping(value ="/count")
	String count(){

		List<StockDataCount> results = new ArrayList<>();

		GroupByResults<StockDataCount> groupByResults = mongoTemplate.group("stock_data_latest",
				GroupBy.key("dataDate")
						.initialDocument("{ count: 0 }")
						.reduceFunction("function(doc, prev) { prev.count += 1 }"),
				StockDataCount.class);

		Iterator<StockDataCount> i = groupByResults.iterator();

		while(i.hasNext()){
			StockDataCount item = i.next();
			results.add(item);
		}

		return results.toString();

	}


	@Autowired
	ShepherdStockDataDayDao shepherdStockDataDayDao;

	@Autowired
	StockDataDayDao stockDataDayDao;

	@Autowired
	StockInfoDao stockInfoDao;

	@Autowired
	TransferLogRepository transferLogRepository;




	public void transfer(){

		List<StockInfo> stockInfoList = stockInfoDao.findAll(new Sort(Sort.Direction.ASC, "stockCode"));

		for(StockInfo stockInfo:stockInfoList){
			TransferLog log = transferLogRepository.findOne(stockInfo.getStockCode());
			if(log==null) {
				LOGGER.info("start handle {}", stockInfo.getStockCode());
				transferStock(stockInfo);
			}else{
				LOGGER.info("{} is handled", log.getId());
			}
		}
	}

	public void transferStock(StockInfo stockInfo){
		List<ShepherdStockDataDay> shepherdStockDataDayList = shepherdStockDataDayDao.findAllByStockCodeAndStockTypeOrderByDateTimeAsc(stockInfo.getStockCode(), 1L);
		for(ShepherdStockDataDay shepherdStockDataDay:shepherdStockDataDayList){
			String id = shepherdStockDataDay.getStockCode()+"-"+shepherdStockDataDay.getDateTime();
			StockDataDay stockDataDay = stockDataDayDao.findById(id);
			try {
				if (stockDataDay == null) {
					stockDataDay = createStockDataDay(shepherdStockDataDay, stockInfo);
					//System.out.println("save "+id+" in mongodb");
					stockDataDayDao.save(stockDataDay);
				} else {
					if (stockDataDay.getZongjine() == null || stockDataDay.getZongjine() == 0) {
						stockDataDay.setZongjine(((Double) (getDouble(shepherdStockDataDay.getDealAmount()) / 10000)).longValue());
						stockDataDayDao.save(stockDataDay);
						System.out.println("update " + id + " in mongodb");
					}
				}
			}catch(Exception e){
				LOGGER.error(shepherdStockDataDay.toString());
				throw  e;
			}
		}

		TransferLog log = new TransferLog();
		log.setId(stockInfo.getStockCode());

		transferLogRepository.save(log);
	}

	public StockDataDay createStockDataDay(ShepherdStockDataDay stockData, StockInfo stockInfo){
		StockDataDay dayData = new StockDataDay();
		dayData.setId(stockData.getStockCode()+"-"+stockData.getDateTime());



		dayData.setCode(stockInfo.getStockCode() + "");
		dayData.setName(stockInfo.getStockName());
//		dayData.setZuoshou(stockData.getZuoshou());
		dayData.setKaipanjia(getFenFromYuan(stockData.getOpenPrice()));
		dayData.setShoupanjia(getFenFromYuan(stockData.getClosePrice()));
		dayData.setZuigaojia(getFenFromYuan(stockData.getHighPrice()));
		dayData.setZuidijia((getFenFromYuan(stockData.getLowPrice())));
		dayData.setZongjine(((Double)(getDouble(stockData.getDealAmount())/10000)).longValue());
		dayData.setZongshou(((Long)( getLong(stockData.getDealVolume())/100)).intValue());
		dayData.setZhangdiezhi(getFenFromYuan(stockData.getChangeAmount()));
		dayData.setZhangdiefu(parsePercentToPerTenhousandInteger(stockData.getChangeRange()));
		dayData.setJunjia(calJunJia(stockData));
		dayData.setZhenfu(parsePercentToPerTenhousandInteger(stockData.getSwingRange()));
//		dayData.setWeibi(stockData.getWeibi());
//		dayData.setWeicha(stockData.getWeicha());
//		dayData.setZhangsu(stockData.getZhangsu());
//		dayData.setLiangbi(stockData.getLiangbi());
		dayData.setHuanshou(parsePercentToPerTenhousandInteger(stockData.getTurnoverRate()));
		dayData.setDataDate(stockData.getDateTime());
//		dayData.setZongguben(stockData.getZongguben());

		dayData.setId(dayData.getCode()+"-"+dayData.getDataDate());

		return dayData;

	}


	/**
	 * 解析百分比至万分之一的整数,
	 * 比如 1% ---> 100
	 * @return
	 */
	private Integer parsePercentToPerTenhousandInteger(String value){
		if("--".equals(value))
			value = "0";
		value = value.replace(",", "");
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
		if("--".equals(value))
			value = "0";
		value = value.replace(",", "");
		Double percentValue = Double.parseDouble(value);
		Long result = Math.round(percentValue*100);

		return result.intValue();
	}


	private Integer getFenFromYuanDouble(Double percentValue){



		BigDecimal b   =   new   BigDecimal(percentValue);
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();

		Long result = Math.round(f1*100);

		return result.intValue();
	}

	private Integer calJunJia(ShepherdStockDataDay stockData){

		if(stockData.getHighPrice().equals(stockData.getLowPrice()))
			return getFenFromYuan(stockData.getHighPrice());

		double amount = getDouble(stockData.getDealAmount());
		double volumn = getDouble(stockData.getDealVolume());
		if(volumn==0 || amount ==0){
			return 0;
		}



		return getFenFromYuanDouble(amount/volumn);
	}


	private Long getLong(String value){
		value = value.replace(",", "");
		return Long.parseLong(value);
	}

	private Double getDouble(String value){
		value = value.replace(",", "");
		return Double.parseDouble(value);
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(DataTransfer2Application.class, args);
//		ShepherdStockDataDayDao dao = context.getBean(ShepherdStockDataDayDao.class);
//		List<ShepherdStockDataDay> list = dao.withStockCodeQuery("000001");
//		System.out.println(list.size());
//
//
//		StockDataDayDao stockDataDayDao = context.getBean(StockDataDayDao.class);
//		StockDataDay stockDataDay = stockDataDayDao.findById("000001-2016-03-04");
//		System.out.println(stockDataDay);

//		DataTransfer2Application application = context.getBean(DataTransfer2Application.class);
//		application.transfer();

//		DataTransferCorrector application = context.getBean(DataTransferCorrector.class);
//		application.transfer();
	}
	public static void main1(String[] args) {
		DataTransfer2Application app = new DataTransfer2Application();
		String a = "2,030";
		System.out.println(app.getDouble(a)/10000);
		System.out.println(((Double)(app.getDouble(a)/10000)).longValue());
	}
}
