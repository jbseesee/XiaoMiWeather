package com.example.jb.test4.ui.homePage;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.jb.test4.bean.homeBean.DailyFroecastData;

import java.util.Map;

public class DailyDataPar implements Parcelable {
    private Map<String,DailyFroecastData> map;

    public Map<String, DailyFroecastData> getMap() {
        return map;
    }

    public void setMap(Map<String, DailyFroecastData> map) {
        this.map = map;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(map);

    }

    protected DailyDataPar(Parcel in) {
        this.map = in.readHashMap(DailyFroecastData.class.getClassLoader());
    }
    public DailyDataPar() {
    }

    public static final Creator<DailyDataPar> CREATOR = new Creator<DailyDataPar>() {
        @Override
        public DailyDataPar createFromParcel(Parcel in) {
            return new DailyDataPar(in);
        }

        @Override
        public DailyDataPar[] newArray(int size) {
            return new DailyDataPar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
