package com.kingzoo.kingcat.project.olanalysis.daydata.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by gonghongrui on 16/4/17.
 */
public class StockDayDataKafkaReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockDayDataKafkaReader.class);


    public static void main(String[] args) throws InterruptedException, InvalidTopologyException, AuthorizationException, AlreadyAliveException {

        //这个zookeeper地址,用于标记从哪里获取kafka信息
        String zks = "10.0.20.249:2181";
        String topic = "istock";
        String zkRoot = "/istock/olanalysis";

        String id = "stock_day_data";

        BrokerHosts brokerHosts = new ZkHosts(zks);
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, zkRoot, id);
        //解析kafka中的数据
        spoutConfig.scheme = new SchemeAsMultiScheme(new StockDayDataScheme());

        //这个zookeeper用于标记,当前,已经从kafka里读取了多少记录了
        spoutConfig.zkServers = Arrays.asList(new String[]{"10.0.20.249"});
        spoutConfig.zkPort = 2181;
        spoutConfig.startOffsetTime = kafka.api.OffsetRequest.EarliestTime();


        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafka-reader", new KafkaSpout(spoutConfig), 1);
        builder.setBolt("word-splitter", new StockDayDataBolt(), 2).customGrouping("kafka-reader",new StockDayDataStreamGrouping());
//        builder.setBolt("word-splitter", new StockDayDataBolt(), 2).shuffleGrouping("kafka-reader");

//        builder.setBolt("word-counter", new WordCounter()).fieldsGrouping("word-splitter", new Fields("word"));

        Config conf = new Config();

        String name = StockDayDataKafkaReader.class.getSimpleName();

        if (args != null && args.length > 0) {

            conf.setNumWorkers(1);

            StormSubmitter.submitTopologyWithProgressBar(name, conf, builder.createTopology());

        } else {

            conf.setMaxTaskParallelism(3);

            LocalCluster cluster = new LocalCluster();

            cluster.submitTopology(name, conf, builder.createTopology());

//            Thread.sleep(60000);
//            cluster.shutdown();

        }


    }
}
