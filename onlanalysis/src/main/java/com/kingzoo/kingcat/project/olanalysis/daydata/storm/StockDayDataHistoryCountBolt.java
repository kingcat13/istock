package com.kingzoo.kingcat.project.olanalysis.daydata.storm;

import com.kingzoo.kingcat.project.olanalysis.daydata.domain.StockDayData;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang.Validate;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 统计历史数据
 */
public class StockDayDataHistoryCountBolt extends BaseRichBolt {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockDayDataHistoryCountBolt.class);

	private OutputCollector collector;

	private String url;
	private String collectionName;
	private MongoClient client;
	private MongoCollection<Document> collection;


	HashMap<String, List<String>> stockMap;

	private static long count = 0;

	public StockDayDataHistoryCountBolt(String url, String collectionName) {
		Validate.notEmpty(url, "url can not be blank or null");
		Validate.notEmpty(collectionName, "collectionName can not be blank or null");

		this.url = url;
		this.collectionName = collectionName;

	}

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;

		//Creates a MongoURI from the given string.
		MongoClientURI uri = new MongoClientURI(url);

		//Creates a MongoClient described by a URI.
		this.client = new MongoClient(uri);

		//Gets a Database.
		MongoDatabase db = client.getDatabase(uri.getDatabase());

		//Gets a collection.
		this.collection = db.getCollection(collectionName);
	}

	@Override
	public void execute(Tuple input) {
		StockDayData stockDayData = (StockDayData) input.getValue(0);
		LOGGER.info("code:" + stockDayData.getCode());
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
