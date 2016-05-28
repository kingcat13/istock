package com.kingzoo.kingcat.project.olanalysis.daydata.storm;


import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.github.jmkgreen.morphia.query.Query;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;
import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.commons.lang.Validate;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 统计历史数据
 */
public class StockDayDataHistoryCountBolt2 extends BaseRichBolt {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockDayDataHistoryCountBolt2.class);

	private OutputCollector collector;

	private String url;
	private String collectionName;
	Datastore datastore;




	public StockDayDataHistoryCountBolt2(String url, String collectionName) {
		Validate.notEmpty(url, "url can not be blank or null");
		Validate.notEmpty(collectionName, "collectionName can not be blank or null");

		this.url = url;
		this.collectionName = collectionName;

	}

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

		MongoClientURI uri = new MongoClientURI(url);
		Mongo mongo = new MongoClient(uri);
		Morphia morphia = new Morphia();

		datastore = morphia.createDatastore(mongo, uri.getDatabase());


	}

	@Override
	public void execute(Tuple input) {
		StockDataDay stockDayData = (StockDataDay) input.getValue(0);
		LOGGER.info("code:" + stockDayData.getCode());
		BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.put("code",stockDayData.getCode());
//		datastore.save(stockDayData);
		Query query = datastore.createQuery(StockDataDay.class).filter("code",stockDayData.getCode());
		query.order("id");
		long count = query.countAll();
		LOGGER.info("count:"+count);
		List<StockDataDay> list = query.asList();
		Integer huanshou = 1;
		int total = huanshou*100;
		int dates = 0;
		for(int i = list.size()-1;i>0;i--){
			StockDataDay data = list.get(i);
			total = total - data.getHuanshou();
			if(total <= 0){

				break;

			}
			dates = list.size() - i;
		}
		LOGGER.info("换手达到{}%耗费{}天", huanshou, dates);




	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}


	public static void main(String[] args){
		for(int i = 0;i<1000;i++){
			UUID uuid = UUID.randomUUID();
			System.out.println(uuid.toString().replaceAll("-","").toUpperCase());
		}
	}
}
