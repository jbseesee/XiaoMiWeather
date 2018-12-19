package com.example.jb.test4.Model;

import com.example.jb.test4.bean.CityInfo;
import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.db.City;

import java.util.List;

import io.reactivex.Observable;

public interface WeatherDataSoucre<T> {
    Observable<HomeViewData> locateAndGetWeather(long type);
    Observable<HomeViewData> getWeather(long id);
    Observable<HomeViewData> getWeather(CityInfo cityInfo);
    void create(T data);
    void delete(long id);

}
