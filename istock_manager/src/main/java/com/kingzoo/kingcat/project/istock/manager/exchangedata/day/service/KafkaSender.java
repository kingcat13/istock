package com.kingzoo.kingcat.project.istock.manager.exchangedata.day.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataLatest;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 发送数据至kafaka
 * Created by gonghongrui on 16/5/27.
 */
public class KafkaSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSender.class);

	ObjectMapper mapper = new ObjectMapper();

	private String topic;

	Producer producer;

	public KafkaSender(String topic, String zookeeper, String kafka) {

		super();

		this.topic = topic;

		producer = createProducer(zookeeper, kafka);
	}

	public void sendStockDataDay(StockDataLatest stockDataLatest){

		try {
			createProducer().send(new KeyedMessage<Integer, String>(topic, mapper.writeValueAsString(stockDataLatest)));
		} catch (JsonProcessingException e) {
			LOGGER.error("", e);
		}
	}

	public void sendStockDataDay(StockDataDay stockDataDay){

		try {
			createProducer().send(new KeyedMessage<Integer, String>(topic, mapper.writeValueAsString(stockDataDay)));
		} catch (JsonProcessingException e) {
			LOGGER.error("", e);
		}
	}


	public void run() {
		Producer producer = createProducer();
		int i = 0;
		while (true) {
			producer.send(new KeyedMessage<Integer, String>(topic, "message: " + i++));
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Producer createProducer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "192.168.1.110:2181,192.168.1.111:2181,192.168.1.112:2181");//声明zk
		properties.put("serializer.class", StringEncoder.class.getName());
		properties.put("metadata.broker.list", "192.168.1.110:9092,192.168.1.111:9093,192.168.1.112:9094");// 声明kafka broker
		return new Producer<Integer, String>(new ProducerConfig(properties));
	}

	private Producer createProducer(String zookeeper, String kafka) {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", zookeeper);//声明zk
		properties.put("serializer.class", StringEncoder.class.getName());
		properties.put("metadata.broker.list", kafka);// 声明kafka broker
		return new Producer<Integer, String>(new ProducerConfig(properties));
	}


	public static void main(String[] args) {

		String stockJson = "{\"id\":\"600798-2016-05-23\",\"version\":null,\"code\":\"600798\",\"dataDate\":\"2016-05-23\",\"name\":\"宁波海运\",\"zuoshou\":\"477\",\"kaipanjia\":\"476\",\"shoupanjia\":\"525\",\"zuigaojia\":\"525\",\"zuidijia\":\"476\",\"zongjine\":\"18308\",\"zhangdiezhi\":\"48\",\"zhangdiefu\":\"1006\",\"junjia\":\"507\",\"zongshou\":\"360815\",\"weibi\":\"10000\",\"weicha\":\"100\",\"zhangsu\":\"0\",\"huanshou\":\"350\",\"zongguben\":\"1030850944\",\"liangbi\":\"391\",\"zhenfu\":\"1027\"}";
		KafkaSender kafkaSender = new KafkaSender("istock", "127.0.0.1:2181", "127.0.0.1:9093");// 使用kafka集群中创建好的主题 test

		kafkaSender.producer.send(new KeyedMessage<Integer, String>("istock", stockJson));

	}
}