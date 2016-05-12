package com.kingzoo.kingcat.project.olanalysis.daydata.storm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingzoo.kingcat.project.olanalysis.daydata.domain.StockDayData;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockDayDataBolt extends BaseRichBolt {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockDayDataBolt.class);

        private OutputCollector collector ;


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
