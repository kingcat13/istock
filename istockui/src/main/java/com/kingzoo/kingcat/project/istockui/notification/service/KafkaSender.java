package com.kingzoo.kingcat.project.istockui.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataLatest;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;


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

		String stockJson = "{\"id\":\"000001-2016-05-23\",\"version\":null,\"code\":\"000001\",\"dataDate\":\"2016-05-23\",\"name\":\"平安银行\",\"zuoshou\":\"1030\",\"kaipanjia\":\"1033\",\"shoupanjia\":\"1028\",\"zuigaojia\":\"1034\",\"zuidijia\":\"1025\",\"zongjine\":\"33330\",\"zhangdiezhi\":\"-2\",\"zhangdiefu\":\"-19\",\"junjia\":\"1030\",\"zongshou\":\"323646\",\"weibi\":\"-1291\",\"weicha\":\"-12\",\"zhangsu\":\"10\",\"huanshou\":\"27\",\"zongguben\":\"-692251648\",\"liangbi\":\"117\",\"zhenfu\":\"87\"}";
		KafkaSender kafkaSender = new KafkaSender("istock", "127.0.0.1:2181", "127.0.0.1:9093");// 使用kafka集群中创建好的主题 test

		kafkaSender.producer.send(new KeyedMessage<Integer, String>("istock", stockJson));

	}
}