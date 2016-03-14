package com.kingzoo.kingcat.project.istock.analysis.livemore;

import com.kingzoo.kingcat.project.istock.analysis.domain.Trend;
import com.kingzoo.kingcat.project.istock.core.Ohlc;
import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;
import com.kingzoo.kingcat.project.istock.manager.analysis.domain.Tag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gonghongrui on 15/8/5.
 */
@Service("livermoreService")
public class LivermoreService {

    /**上涨趋势确立的点数,确立上升趋势,数值每增加一，代表0.01%，比如取值为900时，代表9%*/
    public static int riseTrendFlag = 900;

    public static int downTrendFlag = 900;

    /**震荡幅度20%*/
    public static int swiftRate = 20000;



    /*计算出的一系列标签*/
    List<Tag> tagList;

    /**
     * 分析上升下跌趋势
     * @param stockDataList
     * @return 一组趋势
     */
    public List<Trend> analysis(List<StockDataDay> stockDataList){

        List<Trend> result = new ArrayList<>();

        //分析所有数据

        return result;
    }

    /**
     * 查找最初的走势
     */
    public Trend findOriginalTrend(List<Ohlc> ohlcList){

        Ohlc highest = null, lowest = null;
        Ohlc highestBeforeLowest = null;
        Ohlc lowestBeforeHighest = null;

        /*
        * 确定上升趋势
        * 0.第一点时，最高价与最低价都是该价格
        * 1.当前价格比最低点之前的最高价格高指定比率时，确立上升趋势
        * 2.最低点之前无最高价，则比最高价高指定比率时，确立上升趋势
        * 3.当前价格比最高点之前的最低价低指定的比率时，确定下降趋势
        * 4.最高点之前无最低价，则比最低价低指定比率时，确定下降趋势
        */

        //0.
        highest = ohlcList.get(0);
        lowest = ohlcList.get(0);
        Ohlc tempOhlc ;

        Trend trend = new Trend();
        trend.trendType = Trend.TRENDTYPE_EMPTY;
        trend.startTime = ohlcList.get(0).getDataTime();
        for(int i = 1; i < ohlcList.size(); i++){

            tempOhlc = ohlcList.get(i);

            if(tempOhlc.getClose() > highest.getClose()){
                highest = tempOhlc;
                //创了新高，先前的最低点就是"最高点之前的最低点"
                lowestBeforeHighest = lowest;
            }

            if(tempOhlc.getClose() < lowest.getClose()){
                lowest = tempOhlc;
                //创了新低，先前的最高点就是"最低点之前的最高点"
                highestBeforeLowest = highest;
            }

            //如果满足了4个时间点,再进行处理判断，当前是否处于某种趋势中
            if(i < 4) continue;

            //1.上涨达到比率，则上涨趋势确立
            if(tempOhlc.getClose() >= (1 + riseTrendFlag/1000) * highestBeforeLowest.getClose()){
                trend.trendType = Trend.TRENDTYPE_UP;
                trend.endTime = tempOhlc.getDataTime();
                trend.high = highest;
                trend.low = lowest;
                break;
            }
            //2.
            if(highestBeforeLowest == null && (tempOhlc.getClose() > (1 + riseTrendFlag/1000) * highest.getClose())){
                trend.trendType = Trend.TRENDTYPE_UP;
                trend.endTime = tempOhlc.getDataTime();
                trend.high = highest;
                trend.low = lowest;
                break;
            }
            //3.
            if(tempOhlc.getClose() <= (1 - downTrendFlag/1000) * lowestBeforeHighest.getClose()){
                trend.trendType = Trend.TRENDTYPE_DOWN;
                trend.endTime = tempOhlc.getDataTime();
                trend.high = highest;
                trend.low = lowest;
                break;
            }
            //4.
            if(lowestBeforeHighest == null && tempOhlc.getClose() < (1 - downTrendFlag/1000) * lowest.getClose()){
                trend.trendType = Trend.TRENDTYPE_DOWN;
                trend.endTime = tempOhlc.getDataTime();
                trend.high = highest;
                trend.low = lowest;
                break;
            }

        }

        return trend;
    }

    /**
     * 查找上升趋势，上涨30%
     */
    public void findRiseTrend(){}

    /**
     * 查找下降趋势
     */
    public void findFallTrend(){}

    public void findReact(){}
}
