package com.kingzoo.kingcat.project.olanalysis.daydata.storm;

import com.kingzoo.kingcat.project.istock.core.dataday.domain.StockDataDay;

import org.apache.storm.generated.GlobalStreamId;
import org.apache.storm.grouping.CustomStreamGrouping;
import org.apache.storm.task.WorkerTopologyContext;

import java.io.Serializable;
import java.util.*;

public class StockDayDataStreamGrouping implements CustomStreamGrouping, Serializable {

    private ArrayList<List<Integer>> choices;


    @Override
    public void prepare(WorkerTopologyContext context, GlobalStreamId stream, List<Integer> targetTasks) {

        choices = new ArrayList<>(targetTasks.size());
        for (Integer i: targetTasks) {
            choices.add(Arrays.asList(i));
        }

    }

    @Override
    public List<Integer> chooseTasks(int taskId, List<Object> values) {

        int code = 0;
        int size = choices.size();

        StockDataDay stockDayData = (StockDataDay)values.get(0);
        if(stockDayData!=null) {
            code = Integer.parseInt(stockDayData.getCode());
        }

        return choices.get(code%size);



    }

}