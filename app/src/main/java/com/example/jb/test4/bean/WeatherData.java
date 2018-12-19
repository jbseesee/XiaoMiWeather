package com.example.jb.test4.bean;


import com.example.jb.test4.gson.Forecast;
import com.example.jb.test4.gson.Realtime;

public class WeatherData {
    private Realtime realtime;
    private Forecast forecast;
    private CityInfo cityInfo;

    public Realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }
}
