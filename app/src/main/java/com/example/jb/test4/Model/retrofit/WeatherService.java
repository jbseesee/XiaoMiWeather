package com.example.jb.test4.Model.retrofit;



import com.example.jb.test4.gson.Forecast;
import com.example.jb.test4.gson.Realtime;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {

    @GET("{location}/realtime.json")
    Observable<Realtime> getRealtimeWeather(@Path("location") String location);

    @GET("{location}/forecast.json")
    Observable<Forecast> getForecastWeather(@Path("location") String location);

}
