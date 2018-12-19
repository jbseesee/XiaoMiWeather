package com.example.jb.test4.bean.homeBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 666 on 2018/5/18.
 */

public class ChatViewData implements Parcelable {
    private String[] time     = new String[49];
    private String[] weather  = new String[49];
    private int[] temperature = new int[49];
    private int[] aqi         = new int[49];
    private String[] wind_speed  = new String[49];
    private int[] directIcon  = new int[49];
    private String[] aqiLevel = new String[49];
    private int[] aqiColor = new int[49];

    private String[] weatherSkycon = new String[49];
    private double[] windDirect = new double[49];

    public ChatViewData(){}

    protected ChatViewData(Parcel in) {
        time = in.createStringArray();
        weather = in.createStringArray();
        temperature = in.createIntArray();
        aqi = in.createIntArray();
        wind_speed = in.createStringArray();
        directIcon = in.createIntArray();
        aqiLevel = in.createStringArray();
        aqiColor = in.createIntArray();
        weatherSkycon = in.createStringArray();
        windDirect = in.createDoubleArray();
    }

    public static final Creator<ChatViewData> CREATOR = new Creator<ChatViewData>() {
        @Override
        public ChatViewData createFromParcel(Parcel in) {
            return new ChatViewData(in);
        }

        @Override
        public ChatViewData[] newArray(int size) {
            return new ChatViewData[size];
        }
    };

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public String[] getWeather() {
        return weather;
    }

    public void setWeather(String[] weather) {
        this.weather = weather;
    }

    public int[] getTemperature() {
        return temperature;
    }

    public void setTemperature(int[] temperature) {
        this.temperature = temperature;
    }

    public int[] getAqi() {
        return aqi;
    }

    public void setAqi(int[] aqi) {
        this.aqi = aqi;
    }

    public String[] getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String[] wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int[] getDirectIcon() {
        return directIcon;
    }

    public void setDirectIcon(int[] directIcon) {
        this.directIcon = directIcon;
    }

    public String[] getAqiLevel() {
        return aqiLevel;
    }

    public void setAqiLevel(String[] aqiLevel) {
        this.aqiLevel = aqiLevel;
    }

    public int[] getAqiColor() {
        return aqiColor;
    }

    public void setAqiColor(int[] aqiColor) {
        this.aqiColor = aqiColor;
    }

    public String[] getWeatherSkycon() {
        return weatherSkycon;
    }

    public void setWeatherSkycon(String[] weatherSkycon) {
        this.weatherSkycon = weatherSkycon;
    }

    public double[] getWindDirect() {
        return windDirect;
    }

    public void setWindDirect(double[] windDirect) {
        this.windDirect = windDirect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(time);
        dest.writeStringArray(weather);
        dest.writeIntArray(temperature);
        dest.writeIntArray(aqi);
        dest.writeStringArray(wind_speed);
        dest.writeIntArray(directIcon);
        dest.writeStringArray(aqiLevel);
        dest.writeIntArray(aqiColor);
        dest.writeStringArray(weatherSkycon);
        dest.writeDoubleArray(windDirect);
    }
}
