package com.example.jb.test4.gson;

import java.util.Date;

/**
 * Created by 666 on 2018/5/3.
 */

public class Aqi {
    public double getValue() {
        return value;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    private double value;
    private Date datetime;
}
