package com.kingzoo.kingcat.project.istockui.core.stock.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 股票指标(开盘收盘等等）
 * Created by gonghongrui on 14/12/11.
 */
@Document
public class StockSpecification {

    @Id
    private Long id;

    private String name;

    private String code;

    private StockSpecificationType type = StockSpecificationType.SIMPLE;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StockSpecificationType getType() {
        return type;
    }

    public void setType(StockSpecificationType type) {
        this.type = type;
    }
}
