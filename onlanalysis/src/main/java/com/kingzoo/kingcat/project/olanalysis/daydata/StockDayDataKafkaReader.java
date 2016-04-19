package com.kingzoo.kingcat.project.olanalysis.daydata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingzoo.kingcat.project.olanalysis.StockDayData;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;

import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by gonghongrui on 16/4/17.
 */
public class StockDayDataKafkaReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockDayDataKafkaReader.class);

    public static class StockDayDataBolt extends BaseRichBolt {

        private OutputCollector collector ;

        ObjectMapper mapper = new ObjectMapper();

        HashMap<String, List<String>> stockMap;

        private static long count = 0;

        @Override
        public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
            this.collector = outputCollector;
            stockMap= new HashMap<>();

            topologyContext.getComponentTasks("");

        }

        @Override
        public void execute(Tuple tuple) {
//            String line = tuple.getString(0);


            try {
                StockDayData stockDayData = (StockDayData)tuple.getValue(0);
//                StockDayData stockDayData = mapper.readValue(line, StockDayData.class);

                List<String> stockList = this.stockMap.get(stockDayData.getCode());

                if(stockList == null) {
                    stockList = new ArrayList<>();
                    this.stockMap.put(stockDayData.getCode(), stockList);
                }

                stockList.add(stockDayData.getId());


                LOGGER.info("RECV["+(stockDayData.getCode())+"] " + stockList);

                collector.ack(tuple);

            } catch (Exception e) {
                e.printStackTrace();
            }

//            LOGGER.info("RECV["+(++count)+"] " + line);

        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("default"));
        }
    }

    public static void main(String[] args) throws InterruptedException, InvalidTopologyException, AuthorizationException, AlreadyAliveException {

        String zks = "10.0.20.89:2181,10.0.20.91:2181";
        String topic = "istock";
        String zkRoot = "/olanalysis";

        String id = "stock_day_data";

        BrokerHosts brokerHosts = new ZkHosts(zks);
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, zkRoot, id);
        //解析kafka中的数据
        spoutConfig.scheme = new SchemeAsMultiScheme(new StockDayDataScheme());
        spoutConfig.zkServers = Arrays.asList(new String[]{"10.0.20.89", "10.0.20.91"});
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
