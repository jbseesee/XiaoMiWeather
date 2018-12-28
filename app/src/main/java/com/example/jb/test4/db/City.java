package com.example.jb.test4.db;

import org.litepal.crud.LitePalSupport;

public class City extends LitePalSupport {


    private long id;
    private String location;
    private String province;
    private String city;
    private String district;
    private String street;
    private long updateTime;
    private int temperture;
    private int maxTm;
    private int minTm;
    private String weatherText;
    private String aqiQuality;
    private String windDirect;
    private String windLevel;
    private String humidity;
    private boolean isLocation = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getTemperture() {
        return temperture;
    }

    public void setTemperture(int temperture) {
        this.temperture = temperture;
    }

    public int getMaxTm() {
        return maxTm;
    }

    public void setMaxTm(int maxTm) {
        this.maxTm = maxTm;
    }

    public int getMinTm() {
        return minTm;
    }

    public void setMinTm(int minTm) {
        this.minTm = minTm;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public String getAqiQuality() {
        return aqiQuality;
    }

    public void setAqiQuality(String aqiQuality) {
        this.aqiQuality = aqiQuality;
    }

    public String getWindDirect() {
        return windDirect;
    }

    public void setWindDirect(String windDirect) {
        this.windDirect = windDirect;
    }

    public String getWindLevel() {
        return windLevel;
    }

    public void setWindLevel(String windLevel) {
        this.windLevel = windLevel;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }
}
