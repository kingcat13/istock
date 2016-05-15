package com.kingzoo.kingcat.project.olanalysis.daydata.storm;

import com.kingzoo.kingcat.project.olanalysis.daydata.domain.StockDayData;
import org.apache.storm.mongodb.bolt.AbstractMongoBolt;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * 统计历史数据
 */
public class StockDayDataHistoryCountBolt extends AbstractMongoBolt {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockDayDataHistoryCountBolt.class);

        private OutputCollector collector ;


        HashMap<String, List<String>> stockMap;

        private static long count = 0;

    public StockDayDataHistoryCountBolt(String url, String collectionName) {
        super(url, collectionName);

    }


    @Override
    public void execute(Tuple input) {
        StockDayData stockDayData = (StockDayData)input.getValue(0);
        LOGGER.info("code:"+stockDayData.getCode());
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
