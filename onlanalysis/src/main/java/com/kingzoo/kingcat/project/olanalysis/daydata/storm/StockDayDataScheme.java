package com.kingzoo.kingcat.project.olanalysis.daydata.storm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingzoo.kingcat.project.olanalysis.daydata.domain.StockDayData;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class StockDayDataScheme extends StringScheme {

    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    public static final String SCHEME_KEY = "stockDayData";

    public static final ObjectMapper mapper = new ObjectMapper();

    public List<Object> deserialize(ByteBuffer bytes) {
        return new Values(deserializeStockDayData(bytes));
    }

    public static StockDayData deserializeStockDayData(ByteBuffer bytes){
        StockDayData stockDayData = null;
        try {
            stockDayData = mapper.readValue(deserializeString(bytes), StockDayData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockDayData;
    }

    public static String deserializeString(ByteBuffer string) {
        if (string.hasArray()) {
            int base = string.arrayOffset();
            return new String(string.array(), base + string.position(), string.remaining());
        } else {
            return new String(Utils.toByteArray(string), UTF8_CHARSET);
        }
    }

    public Fields getOutputFields() {
        return new Fields(SCHEME_KEY);
//        return new Fields("default");
    }
}