package com.example.jb.test4.bean.homeBean;

import android.os.Parcel;
import android.os.Parcelable;

public class DailyFroecastData  implements Parcelable {

    private String[] timeW;//星期几
    private String[] timeD;//几月几号
    private String[] skycon;
    private int[] maxTem;
    private int[] minTem;
    private String[] skycon20h;
    private String[] windDirection;
    private String[] windLevel;
    private String[] aqiQuality;
    private String[] weather;
    private String[] weather20h;
    private int[] weatherIcon;
    private int[] weather20hIcon;
    private int[] aqiColor;

    public DailyFroecastData(){}

    protected DailyFroecastData(Parcel in) {
        timeW = in.createStringArray();
        timeD = in.createStringArray();
        skycon = in.createStringArray();
        maxTem = in.createIntArray();
        minTem = in.createIntArray();
        skycon20h = in.createStringArray();
        windDirection = in.createStringArray();
        windLevel = in.createStringArray();
        aqiQuality = in.createStringArray();
        weather = in.createStringArray();
        weather20h = in.createStringArray();
        weatherIcon = in.createIntArray();
        weather20hIcon = in.createIntArray();
        aqiColor = in.createIntArray();
    }

    public static final Creator<DailyFroecastData> CREATOR = new Creator<DailyFroecastData>() {
        @Override
        public DailyFroecastData createFromParcel(Parcel in) {
            return new DailyFroecastData(in);
        }

        @Override
        public DailyFroecastData[] newArray(int size) {
            return new DailyFroecastData[size];
        }
    };

    public String[] getTimeW() {
        return timeW;
    }

    public void setTimeW(String[] timeW) {
        this.timeW = timeW;
    }

    public String[] getTimeD() {
        return timeD;
    }

    public void setTimeD(String[] timeD) {
        this.timeD = timeD;
    }

    public String[] getSkycon() {
        return skycon;
    }

    public void setSkycon(String[] skycon) {
        this.skycon = skycon;
    }

    public int[] getMaxTem() {
        return maxTem;
    }

    public void setMaxTem(int[] maxTem) {
        this.maxTem = maxTem;
    }

    public int[] getMinTem() {
        return minTem;
    }

    public void setMinTem(int[] minTem) {
        this.minTem = minTem;
    }


    public String[] getSkycon20h() {
        return skycon20h;
    }

    public void setSkycon20h(String[] skycon20h) {
        this.skycon20h = skycon20h;
    }

    public String[] getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String[] windDirection) {
        this.windDirection = windDirection;
    }

    public String[] getWindLevel() {
        return windLevel;
    }

    public void setWindLevel(String[] windLevel) {
        this.windLevel = windLevel;
    }

    public String[] getAqiQuality() {
        return aqiQuality;
    }

    public void setAqiQuality(String[] aqiQuality) {
        this.aqiQuality = aqiQuality;
    }

    public int[] getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int[] weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public int[] getWeather20hIcon() {
        return weather20hIcon;
    }

    public void setWeather20hIcon(int[] weather20hIcon) {
        this.weather20hIcon = weather20hIcon;
    }

    public int[] getAqiColor() {
        return aqiColor;
    }

    public void setAqiColor(int[] aqiColor) {
        this.aqiColor = aqiColor;
    }

    public String[] getWeather() {
        return weather;
    }

    public void setWeather(String[] weather) {
        this.weather = weather;
    }

    public String[] getWeather20h() {
        return weather20h;
    }

    public void setWeather20h(String[] weather20h) {
        this.weather20h = weather20h;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(timeW);
        dest.writeStringArray(timeD);
        dest.writeStringArray(skycon);
        dest.writeIntArray(maxTem);
        dest.writeIntArray(minTem);
        dest.writeStringArray(skycon20h);
        dest.writeStringArray(windDirection);
        dest.writeStringArray(windLevel);
        dest.writeStringArray(aqiQuality);
        dest.writeStringArray(weather);
        dest.writeStringArray(weather20h);
        dest.writeIntArray(weatherIcon);
        dest.writeIntArray(weather20hIcon);
        dest.writeIntArray(aqiColor);
    }
}
