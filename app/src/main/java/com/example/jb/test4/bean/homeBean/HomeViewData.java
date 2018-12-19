package com.example.jb.test4.bean.homeBean;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.jb.test4.bean.CityInfo;

public class HomeViewData implements Parcelable {
    private long id;
    private RealTimeData realTimeData;
    private ThreeDaysData threeDaysData;
    private ChatViewData chatViewData;
    private DailyFroecastData dailyFroecastData;
    private SunViewData sunViewData;
    private CityInfo cityInfo;

    public HomeViewData(){

    }

    protected HomeViewData(Parcel in) {
        id = in.readLong();
        realTimeData = in.readParcelable(RealTimeData.class.getClassLoader());
        threeDaysData = in.readParcelable(ThreeDaysData.class.getClassLoader());
        chatViewData = in.readParcelable(ChatViewData.class.getClassLoader());
        dailyFroecastData = in.readParcelable(DailyFroecastData.class.getClassLoader());
        sunViewData = in.readParcelable(SunViewData.class.getClassLoader());
        cityInfo = in.readParcelable(CityInfo.class.getClassLoader());
    }

    public static final Creator<HomeViewData> CREATOR = new Creator<HomeViewData>() {
        @Override
        public HomeViewData createFromParcel(Parcel in) {
            return new HomeViewData(in);
        }

        @Override
        public HomeViewData[] newArray(int size) {
            return new HomeViewData[size];
        }
    };

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public RealTimeData getRealTimeData() {
        return realTimeData;
    }

    public void setRealTimeData(RealTimeData realTimeData) {
        this.realTimeData = realTimeData;
    }

    public ThreeDaysData getThreeDaysData() {
        return threeDaysData;
    }

    public void setThreeDaysData(ThreeDaysData threeDaysData) {
        this.threeDaysData = threeDaysData;
    }

    public ChatViewData getChatViewData() {
        return chatViewData;
    }

    public void setChatViewData(ChatViewData chatViewData) {
        this.chatViewData = chatViewData;
    }

    public DailyFroecastData getDailyFroecastData() {
        return dailyFroecastData;
    }

    public void setDailyFroecastData(DailyFroecastData dailyFroecastData) {
        this.dailyFroecastData = dailyFroecastData;
    }

    public SunViewData getSunViewData() {
        return sunViewData;
    }

    public void setSunViewData(SunViewData sunViewData) {
        this.sunViewData = sunViewData;
    }


    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(realTimeData, flags);
        dest.writeParcelable(threeDaysData, flags);
        dest.writeParcelable(chatViewData, flags);
        dest.writeParcelable(dailyFroecastData, flags);
        dest.writeParcelable(sunViewData, flags);
        dest.writeParcelable(cityInfo, flags);
    }
}
