package com.example.jb.test4.bean.homeBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 666 on 2018/5/18.
 */

public class SunViewData  implements Parcelable {


    private long sunRiseL;
    private long sunSetL;
    private String sunRiseS;
    private String sunSetS;
    private String windDirect;
    private String windLevel;
    private String humidity;
    private String temperature;
    private String pressure;

    public SunViewData(){
    }

    protected SunViewData(Parcel in) {
        sunRiseL = in.readLong();
        sunSetL = in.readLong();
        sunRiseS = in.readString();
        sunSetS = in.readString();
        windDirect = in.readString();
        windLevel = in.readString();
        humidity = in.readString();
        temperature = in.readString();
        pressure = in.readString();
    }

    public static final Creator<SunViewData> CREATOR = new Creator<SunViewData>() {
        @Override
        public SunViewData createFromParcel(Parcel in) {
            return new SunViewData(in);
        }

        @Override
        public SunViewData[] newArray(int size) {
            return new SunViewData[size];
        }
    };

    public long getSunRiseL() {
        return sunRiseL;
    }

    public void setSunRiseL(long sunRiseL) {
        this.sunRiseL = sunRiseL;
    }

    public long getSunSetL() {
        return sunSetL;
    }

    public void setSunSetL(long sunSetL) {
        this.sunSetL = sunSetL;
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

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSunRiseS() {
        return sunRiseS;
    }

    public void setSunRiseS(String sunRiseS) {
        this.sunRiseS = sunRiseS;
    }

    public String getSunSetS() {
        return sunSetS;
    }

    public void setSunSetS(String sunSetS) {
        this.sunSetS = sunSetS;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(sunRiseL);
        dest.writeLong(sunSetL);
        dest.writeString(sunRiseS);
        dest.writeString(sunSetS);
        dest.writeString(windDirect);
        dest.writeString(windLevel);
        dest.writeString(humidity);
        dest.writeString(temperature);
        dest.writeString(pressure);
    }
}
