package com.kingzoo.kingcat.project.olanalysis.daydata.storm;

import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;
import org.bson.BsonElement;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

/**
 * Created by gonghongrui on 16/5/28.
 */
public class StockDataDayCodec implements Codec<StockDataDay> {
	@Override
	public StockDataDay decode(BsonReader reader, DecoderContext decoderContext) {
		StockDataDay stockDataDay = new StockDataDay();

		reader.readStartDocument();

		while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
			String fieldName = reader.readName();
			switch (fieldName){
				case "_id":stockDataDay.setId(reader.readString());break;
				case "_class":reader.readString();break;
				case "code":stockDataDay.setCode(reader.readString());break;
				case "dataDate":stockDataDay.setDataDate(reader.readString());break;
				case "name":stockDataDay.setName(reader.readString());break;
				case "zuoshou":stockDataDay.setZuoshou(reader.readInt32());break;
				case "kaipanjia":stockDataDay.setKaipanjia(reader.readInt32());break;
				case "shoupanjia":stockDataDay.setShoupanjia(reader.readInt32());break;
				case "zuigaojia":stockDataDay.setZuigaojia(reader.readInt32());break;
				case "zuidijia":stockDataDay.setZuidijia(reader.readInt32());break;

				case "zongjine":
					if(reader.getCurrentBsonType()==BsonType.DOUBLE)
						stockDataDay.setZongjine(((Double)reader.readDouble()).longValue());
					else if(reader.getCurrentBsonType()==BsonType.INT32)
						stockDataDay.setZongjine(((Integer)reader.readInt32()).longValue());
					else if(reader.getCurrentBsonType()==BsonType.INT64)
						stockDataDay.setZongjine((reader.readInt64()));

					break;

				case "zhangdiezhi":stockDataDay.setZhangdiezhi(reader.readInt32());break;
				case "zhangdiefu":stockDataDay.setZhangdiefu(reader.readInt32());break;
				case "junjia":stockDataDay.setJunjia(reader.readInt32());break;
				case "zongshou":stockDataDay.setZongshou(reader.readInt32());break;
				case "weibi":stockDataDay.setWeibi(reader.readInt32());break;
				case "weicha":stockDataDay.setWeicha(reader.readInt32());break;
				case "zhangsu":stockDataDay.setZhangsu(reader.readInt32());break;
				case "huanshou":stockDataDay.setHuanshou(reader.readInt32());break;

				case "zongguben":
					if(reader.getCurrentBsonType()==BsonType.DOUBLE)
						stockDataDay.setZongguben(((Double)reader.readDouble()).longValue());
					else if(reader.getCurrentBsonType()==BsonType.INT32)
						stockDataDay.setZongguben(((Integer)reader.readInt32()).longValue());
					else if(reader.getCurrentBsonType()==BsonType.INT64)
						stockDataDay.setZongguben((reader.readInt64()));

					break;
				case "liangbi":stockDataDay.setLiangbi(reader.readInt32());break;
				case "zhenfu":stockDataDay.setZhenfu(reader.readInt32());break;
			}

		}


		reader.readEndDocument();
		return stockDataDay;
	}

	@Override
	public void encode(BsonWriter writer, StockDataDay value, EncoderContext encoderContext) {
		writer.writeStartDocument();

		writer.writeObjectId(new ObjectId(value.getId()));
		writer.writeString("code", value.getCode());
		writer.writeEndDocument();
	}

	@Override
	public Class<StockDataDay> getEncoderClass() {
		return StockDataDay.class;
	}
}
