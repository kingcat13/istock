package com.kingzoo.kingcat.project.dataGetter.daydatagetter.eastmoney;

import java.util.ArrayList;

/**
 * Created by gonghongrui on 14/11/16.
 */
public class PageData {
    private int pages;
    private ArrayList<String> rank;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public ArrayList<String> getRank() {
        return rank;
    }

    public void setRank(ArrayList<String> rank) {
        this.rank = rank;
    }
}
