package com.example.jb.test4.gson;

import java.util.Date;

/**
 * Created by 666 on 2018/5/4.
 */

public class Skycon {
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    private String value;
    private Date datetime;
    
}
