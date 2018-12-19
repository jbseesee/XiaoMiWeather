package com.example.jb.test4.bean.homeBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 666 on 2018/5/18.
 */

public class ThreeDaysData implements Parcelable {
    private String[] days = {"今天","明天","后天"};
    private int[] weatherIcon = new int[3];
    private String[] weather = new String[3];
    private String[] aqiQuality = new String[3];
    private int[] maxT = new int[3];
    private int[] minT = new int[3];
    public ThreeDaysData(){}


    protected ThreeDaysData(Parcel in) {
        days = in.createStringArray();
        weatherIcon = in.createIntArray();
        weather = in.createStringArray();
        aqiQuality = in.createStringArray();
        maxT = in.createIntArray();
        minT = in.createIntArray();
    }

    public static final Creator<ThreeDaysData> CREATOR = new Creator<ThreeDaysData>() {
        @Override
        public ThreeDaysData createFromParcel(Parcel in) {
            return new ThreeDaysData(in);
        }

        @Override
        public ThreeDaysData[] newArray(int size) {
            return new ThreeDaysData[size];
        }
    };

    public String[] getWeather() {
        return weather;
    }

    public void setWeather(String[] weather) {
        this.weather = weather;
    }

    public String[] getAqiQuality() {
        return aqiQuality;
    }

    public void setAqiQuality(String[] aqiQuality) {
        this.aqiQuality = aqiQuality;
    }

    public int[] getMaxT() {
        return maxT;
    }

    public void setMaxT(int[] maxT) {
        this.maxT = maxT;
    }

    public int[] getMinT() {
        return minT;
    }

    public void setMinT(int[] minT) {
        this.minT = minT;
    }

    public String[] getDays() {
        return this.days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public int[] getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int[] weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(days);
        dest.writeIntArray(weatherIcon);
        dest.writeStringArray(weather);
        dest.writeStringArray(aqiQuality);
        dest.writeIntArray(maxT);
        dest.writeIntArray(minT);
    }
}
