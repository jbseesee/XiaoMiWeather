package com.example.jb.test4.gson;

import java.util.Date;

/**
 * Created by 666 on 2018/5/3.
 */

public class Wind {
    public double getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    private double direction;
    private double speed;
    private Date datetime;
}
