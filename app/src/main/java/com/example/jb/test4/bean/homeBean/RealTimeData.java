package com.example.jb.test4.bean.homeBean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 666 on 2018/5/18.
 */

public class RealTimeData implements Parcelable {

    private int temperature;
    private String district;
    private String street;
    private String weather;
    private int aqiIcon;
    private int aqi;
    private String aqiQuality;
    private String description;
    private String weatherValue;

    public RealTimeData(){}

    protected RealTimeData(Parcel in) {
        temperature = in.readInt();
        district = in.readString();
        street = in.readString();
        weather = in.readString();
        aqiIcon = in.readInt();
        aqi = in.readInt();
        aqiQuality = in.readString();
        description = in.readString();
        weatherValue = in.readString();
    }

    public static final Creator<RealTimeData> CREATOR = new Creator<RealTimeData>() {
        @Override
        public RealTimeData createFromParcel(Parcel in) {
            return new RealTimeData(in);
        }

        @Override
        public RealTimeData[] newArray(int size) {
            return new RealTimeData[size];
        }
    };

    public String getAqiQuality() {
        return aqiQuality;
    }

    public void setAqiQuality(String aqiQuality) {
        this.aqiQuality = aqiQuality;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
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

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAqiIcon() {
        return aqiIcon;
    }

    public void setAqiIcon(int aqiIcon) {
        this.aqiIcon = aqiIcon;
    }

    public String getWeatherValue() {
        return weatherValue;
    }

    public void setWeatherValue(String weatherValue) {
        this.weatherValue = weatherValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(temperature);
        dest.writeString(district);
        dest.writeString(street);
        dest.writeString(weather);
        dest.writeInt(aqiIcon);
        dest.writeInt(aqi);
        dest.writeString(aqiQuality);
        dest.writeString(description);
        dest.writeString(weatherValue);
    }

}
