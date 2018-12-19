package com.example.jb.test4.Model.retrofit;

import com.example.jb.test4.bean.WeatherData;
import com.example.jb.test4.gson.Forecast;
import com.example.jb.test4.gson.Realtime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    private static final String KEY = "q1lhVJwardE6Y9-E/";
    private static final String BACEURL = "https://api.caiyunapp.com/v2/";
    private static HttpUtil mInstance;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private HttpUtil(){
        init();
    }

    public static  HttpUtil getmInstance(){
        if (mInstance == null){
            mInstance = new HttpUtil();
        }
        return mInstance;
    }

    private void init(){
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BACEURL + KEY)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public Observable<WeatherData> creat(String location){

        WeatherService weatherService = retrofit.create(WeatherService.class);
        return Observable
                .zip(weatherService.getRealtimeWeather(location),
                weatherService.getForecastWeather(location),
                new BiFunction<Realtime, Forecast, WeatherData>() {
                    @Override
                    public WeatherData apply(Realtime realtime, Forecast forecast) throws IOException {
                        WeatherData weatherData = new WeatherData();
                        weatherData.setRealtime(realtime);
                        weatherData.setForecast(forecast);
                        if (realtime.getStatus().equals("ok")&&forecast.getStatus().equals("ok")){
                            return weatherData;
                        }else {
                            return null;
                        }
                    }
                });

    }

}
