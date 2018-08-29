package com.pie.model;

import java.util.List;

/**
 * @author:zjh
 * @date:2018/8/28
 * @Description：
 */
public class Result {

    private String stat;
    private List<Data> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "stat='" + stat + '\'' +
                ", data=" + data +
                '}';
    }
}
