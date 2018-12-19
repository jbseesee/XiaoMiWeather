package com.example.jb.test4.Model;


import android.util.Log;

import com.example.jb.test4.bean.homeBean.ChatViewData;
import com.example.jb.test4.bean.CityInfo;
import com.example.jb.test4.bean.homeBean.DailyFroecastData;
import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.util.LoadIconUtil;
import com.example.jb.test4.bean.homeBean.RealTimeData;
import com.example.jb.test4.bean.homeBean.SunViewData;
import com.example.jb.test4.bean.homeBean.ThreeDaysData;
import com.example.jb.test4.bean.WeatherData;
import com.example.jb.test4.db.City;
import com.example.jb.test4.gson.Aqi;
import com.example.jb.test4.gson.Daily;
import com.example.jb.test4.gson.Forecast;
import com.example.jb.test4.gson.Realtime;
import com.example.jb.test4.gson.Skycon;
import com.example.jb.test4.gson.Temperture;
import com.example.jb.test4.gson.Wind;
import com.example.jb.test4.util.WeatherUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 666 on 2018/5/18.
 */

public class HandleJson {
    private HomeViewData homeViewData;
    private CityInfo cityInfo;

    private Realtime realtime;
    private Forecast forecast;

    public HandleJson(){

    }
    public HomeViewData handleData(WeatherData weatherData){
        homeViewData = new HomeViewData();
        cityInfo = weatherData.getCityInfo();
        realtime = weatherData.getRealtime();
        forecast = weatherData.getForecast();
        doit();
        homeViewData.setCityInfo(cityInfo);
        return homeViewData;
    }

    private void doit(){

        saveRealTimeWeather();
        saveThreeDaysForecast();
        saveHourly();
        saveSunRiseNSet();
        saveDailyForecast();

    }

    private void saveRealTimeWeather(){
        RealTimeData realTimeData = new RealTimeData();

        int realTimeTemperature = (int) (realtime.getResult().getTemperature() + 0.5);
        String realTimeWeather = realtime.getResult().getSkycon();
        String realTimeAqiQuality = WeatherUtil.aqiQuality((int)(realtime.getResult().getAqi()+0.5));
        int aqi = realtime.getResult().getAqi();
        String descrption = forecast.getResult().getMinutely().getDescription();
        realTimeData.setTemperature(realTimeTemperature);
        realTimeData.setDistrict(cityInfo.getDistrict());
        realTimeData.setStreet(cityInfo.getStreet());
        realTimeData.setAqiIcon(LoadIconUtil.aqiIcon(realTimeAqiQuality));
        realTimeData.setWeather(WeatherUtil.weatherText(realTimeWeather));
        realTimeData.setAqi(aqi);
        realTimeData.setAqiQuality(realTimeAqiQuality);
        realTimeData.setDescription(descrption);
        realTimeData.setWeatherValue(realTimeWeather);

        homeViewData.setRealTimeData(realTimeData);

    }

    private void saveThreeDaysForecast(){
        String[] threeDaysWeather = new String[3];
        int[] threeDaysIcon = new int[3];
        int[] threeDaysAqi = new int[3];
        int[] threeDaysMax = new int[3];
        int[] threeDaysMin = new int[3];

        for (int i = 0;i < threeDaysWeather.length;i++){
            threeDaysWeather[i] = WeatherUtil.weatherText(forecast.getResult().getDaily().getSkycon().get(i).getValue());
            threeDaysIcon[i] = LoadIconUtil.skyconThreeDays(forecast.getResult().getDaily().getSkycon().get(i).getValue());
            threeDaysAqi[i] = (int) (forecast.getResult().getDaily().getAqi().get(i).getAvg() + 0.5);
            threeDaysMax[i] = (int) (forecast.getResult().getDaily().getTemperature().get(i).getMax() + 0.5);
            threeDaysMin[i] = (int) (forecast.getResult().getDaily().getTemperature().get(i).getMin() + 0.5);
        }

        ThreeDaysData threeDaysData = new ThreeDaysData();


        threeDaysData.setWeather(threeDaysWeather);
        threeDaysData.setWeatherIcon(threeDaysIcon);
        threeDaysData.setAqiQuality(WeatherUtil.aqiLevel(threeDaysAqi));
        threeDaysData.setMaxT(threeDaysMax);
        threeDaysData.setMinT(threeDaysMin);

        homeViewData.setThreeDaysData(threeDaysData);

    }


    private void saveHourly(){

        List<Temperture> tempertures = forecast.getResult().getHourly().getTemperature();
        List<Aqi> aqis = forecast.getResult().getHourly().getAqi();
        List<Skycon> skycons = forecast.getResult().getHourly().getSkycon();
        List<Wind> winds = forecast.getResult().getHourly().getWind();
        String[] timeHHmm = new String[tempertures.size()];
        String[] timeMMdd = new String[tempertures.size()];
        int[] tempertureforecast = new int[tempertures.size() + 1];
        int[] aqiforecast = new int[aqis.size() + 1];
        String[] skyconforecast = new String[skycons.size() + 1];
        int[] windDirectionforecast = new int[winds.size() + 1];
        double[] windSpeedforecast = new double[winds.size() + 1];
        int realTemperature = (int) (realtime.getResult().getTemperature() + 0.5);
        String realskycon = realtime.getResult().getSkycon();
        int realAqi =  realtime.getResult().getAqi();
        double realWindDirection = realtime.getResult().getWind().getDirection();
        double realWindSpeed = realtime.getResult().getWind().getSpeed();

        for (int i = 0; i < tempertureforecast.length; i++) {
            if (i == 0) {
                tempertureforecast[i] = (int) (tempertures.get(i).getValue() + 0.5);
                aqiforecast[i] = (int) aqis.get(i).getValue();
                skyconforecast[i] = skycons.get(i).getValue();
                windDirectionforecast[i] = LoadIconUtil.windDirection(winds.get(i).getDirection());
                windSpeedforecast[i] = winds.get(i).getSpeed();
            } else if (i == 1) {
                tempertureforecast[i] = realTemperature;
                aqiforecast[i] = realAqi;
                skyconforecast[i] = realskycon;
                windDirectionforecast[i] = LoadIconUtil.windDirection(realWindDirection);
                windSpeedforecast[i] = realWindSpeed;
            } else {
                tempertureforecast[i] = (int) (tempertures.get(i - 1).getValue() + 0.5);
                aqiforecast[i] = (int) aqis.get(i - 1).getValue();
                skyconforecast[i] = skycons.get(i - 1).getValue();
                windDirectionforecast[i] = LoadIconUtil.windDirection(winds.get(i - 1).getDirection());
                windSpeedforecast[i] = winds.get(i - 1).getSpeed();
            }
            if (i < tempertureforecast.length - 1) {
                timeHHmm[i] = new SimpleDateFormat("HH:mm").format(tempertures.get(i).getDatetime());
                timeMMdd[i] = new SimpleDateFormat("MM月dd日").format(tempertures.get(i).getDatetime());
            }
        }

        int[] aqiColor = WeatherUtil.aqiColor(aqiforecast);
        String[] aqiLevel = WeatherUtil.aqiLevel(aqiforecast);
        String[] date = WeatherUtil.date(timeHHmm, timeMMdd);
        String[] skycon = skyconforecast;
        String[] winSpeedLevel = WeatherUtil.windSpeedLevel(windSpeedforecast);
        ChatViewData chatViewData = new ChatViewData();
        chatViewData.setAqi(aqiforecast);
        chatViewData.setAqiColor(aqiColor);
        chatViewData.setAqiLevel(aqiLevel);
        chatViewData.setDirectIcon(windDirectionforecast);
        chatViewData.setTemperature(tempertureforecast);
        chatViewData.setTime(date);
        chatViewData.setWeather(skycon);
        chatViewData.setWind_speed(winSpeedLevel);

        homeViewData.setChatViewData(chatViewData);
    }


    private void saveSunRiseNSet(){
        double[] windLevel = {forecast.getResult().getDaily().getWindD().get(0).getAvg().getSpeed()};
        String humidityS = (int)(forecast.getResult().getDaily().getHumidity().get(0).getAvg() * 100) + "%";
        String temperatureS = new DecimalFormat("0.00").format(forecast.getResult().getDaily().getTemperature().get(0).getAvg()) + "℃";
        String pressureS = new DecimalFormat("0.0").format(forecast.getResult().getDaily().getPres().get(0).getAvg() / 100) + "mb";
        Date date = forecast.getResult().getDaily().getAstro().get(0).getDate();
        String sunRiseS = forecast.getResult().getDaily().getAstro().get(0).getSunrise().getTime();
        String sunSetS = forecast.getResult().getDaily().getAstro().get(0).getSunset().getTime();
        long sunRiseL = WeatherUtil.time(new SimpleDateFormat("yyyy-MM-dd").format(date) + " " + sunRiseS);
        long sunSetL = WeatherUtil.time(new SimpleDateFormat("yyyy-MM-dd").format(date) + " " + sunSetS);

        SunViewData sunViewData = new SunViewData();

       sunViewData.setWindDirect(WeatherUtil.windDirectionS(forecast.getResult().getDaily().getWindD().get(0).getAvg().getDirection()));
       sunViewData.setWindLevel(WeatherUtil.windSpeedLevel(windLevel)[0]);
       sunViewData.setHumidity(humidityS);
       sunViewData.setTemperature(temperatureS);
       sunViewData.setPressure(pressureS);
       sunViewData.setSunRiseS(sunRiseS);
       sunViewData.setSunSetS(sunSetS);
       sunViewData.setSunRiseL(sunRiseL);
       sunViewData.setSunSetL(sunSetL);

       homeViewData.setSunViewData(sunViewData);
    }



    private void saveDailyForecast(){
        Daily daily = forecast.getResult().getDaily();
        int length = daily.getSkycon().size();
        String[] timeW = new String[length];
        String[] timeD = new String[length];
        int[]  weather = new int[length];
        String[]  skycon = new String[length];
        int[] maxTem = new int[length];
        int[] minTem = new int[length];
        int[] weather20h = new int[length];
        String[] skycon20h = new String[length];
        String[] windDirection = new String[length];
        double[] windLevel = new double[length];
        String[] aqiQuality = new String[length];
        int[] aqiColor = new int[length];
        for (int i = 0; i < length; i++){

            if (i == 0){
                timeW[i] = "今天";
            }else if (i == 1){
                timeW[i] = "明天";
            }else {
                timeW[i] = WeatherUtil.timeTransfromW(daily.getSkycon_08h_20h().get(i).getDate());
            }
            timeD[i] = WeatherUtil.timeTransfromD(daily.getSkycon_08h_20h().get(i).getDate());
            weather[i] = LoadIconUtil.skycon(daily.getSkycon_08h_20h().get(i).getValue());
            skycon[i] = WeatherUtil.weatherText(daily.getSkycon_08h_20h().get(i).getValue());
            maxTem[i] = (int)daily.getTemperature().get(i).getMax();
            minTem[i] = (int)daily.getTemperature().get(i).getMin();
            weather20h[i] = LoadIconUtil.skycon(daily.getSkycon_20h_32h().get(i).getValue());
            skycon20h[i] = WeatherUtil.weatherText(daily.getSkycon_20h_32h().get(i).getValue());
            windDirection[i] = WeatherUtil.windDirectionS(daily.getWindD().get(i).getAvg().getDirection());
            windLevel[i] = daily.getWindD().get(i).getAvg().getSpeed();
            aqiQuality[i] = WeatherUtil.aqiQuality((int)daily.getAqi().get(i).getAvg());
        }


        DailyFroecastData dailyFroecastData = new DailyFroecastData();
        dailyFroecastData.setTimeW(timeW);
        dailyFroecastData.setTimeD(timeD);
        dailyFroecastData.setWeatherIcon(weather);
        dailyFroecastData.setSkycon(skycon);
        dailyFroecastData.setMaxTem(maxTem);
        dailyFroecastData.setMinTem(minTem);
        dailyFroecastData.setWeather20hIcon(weather20h);
        dailyFroecastData.setSkycon20h(skycon20h);
        dailyFroecastData.setWindDirection(windDirection);
        dailyFroecastData.setWindLevel(WeatherUtil.windSpeedLevel(windLevel));
        dailyFroecastData.setAqiQuality(aqiQuality);
        dailyFroecastData.setAqiColor(aqiColor);

        homeViewData.setDailyFroecastData(dailyFroecastData);
        
    }



}
/*//ViewChatData
        ChatViewData chatViewData = new ChatViewData();
        List<Temperture> tempertures = forecast.getResult().getHourly().getTemperature();
        List<Aqi> aqis = forecast.getResult().getHourly().getAqi();
        List<Skycon> skycons = forecast.getResult().getHourly().getSkycon();
        List<Wind> winds = forecast.getResult().getHourly().getWind();
        String[] timeHHmm = new String[tempertures.size()];
        String[] timeMMdd = new String[tempertures.size()];
        int[] tempertureforecast = new int[tempertures.size() + 1];
        int[] aqiforecast = new int[aqis.size() + 1];
        String[] skyconforecast = new String[skycons.size() + 1];
        double[] windDirectionforecast = new double[winds.size() + 1];
        double[] windSpeedforecast = new double[winds.size() + 1];
        int realTemperature = (int) (realtime.getResult().getTemperature() + 0.5);
        String realskycon = realtime.getResult().getSkycon();
        int realAqi =  realtime.getResult().getAqi();
        double realWindDirection = realtime.getResult().getWind().getDirection();
        double realWindSpeed = realtime.getResult().getWind().getSpeed();

        for (int i = 0; i < tempertureforecast.length; i++) {
            if (i == 0) {
                tempertureforecast[i] = (int) (tempertures.get(i).getValue() + 0.5);
                aqiforecast[i] = (int) aqis.get(i).getValue();
                skyconforecast[i] = skycons.get(i).getValue();
                windDirectionforecast[i] = winds.get(i).getDirection();
                windSpeedforecast[i] = winds.get(i).getSpeed();
            } else if (i == 1) {
                tempertureforecast[i] = realTemperature;
                aqiforecast[i] = realAqi;
                skyconforecast[i] = realskycon;
                windDirectionforecast[i] = realWindDirection;
                windSpeedforecast[i] = realWindSpeed;
            } else {
                tempertureforecast[i] = (int) (tempertures.get(i - 1).getValue() + 0.5);
                aqiforecast[i] = (int) aqis.get(i - 1).getValue();
                skyconforecast[i] = skycons.get(i - 1).getValue();
                windDirectionforecast[i] = winds.get(i - 1).getDirection();
                windSpeedforecast[i] = winds.get(i - 1).getSpeed();
            }
            if (i < tempertureforecast.length - 1) {
                timeHHmm[i] = new SimpleDateFormat("HH:mm").format(tempertures.get(i).getDatetime());
                timeMMdd[i] = new SimpleDateFormat("MM月dd日").format(tempertures.get(i).getDatetime());
            }
        }

        chatViewData.setAqi(aqiforecast);
        chatViewData.setAqiColor(aqiColor(aqiforecast));
        chatViewData.setAqiLevel(aqiLevel(aqiforecast));
        chatViewData.setDirectIcon(windDirection(windDirectionforecast));
        chatViewData.setTemperature(tempertureforecast);
        chatViewData.setTime(date(timeHHmm,timeMMdd));
        chatViewData.setWeather(skycon(skyconforecast));
        chatViewData.setWind_speed(windSpeedLevel(windSpeedforecast));

//RealtimeWeather.class
        RealtimeWeather realtimeWeather = LitePal
        RealTimeData realTimeData = new RealTimeData();
        String realWeather = realWeather(realskycon);
        String realAqiQuality = aqiQuality((int)(realtime.getResult().getAqi()+0.5));
        String descrption = forecast.getResult().getMinutely().getDescription();
        int aqiIcon = aqiIcon(realAqiQuality);
        realTimeData.setTemperature(realTemperature);
        realTimeData.setDistrict(district);
        realTimeData.setStreet(street);
        realTimeData.setWeather(realWeather);
        realTimeData.setAqiIcon(aqiIcon);
        realTimeData.setAqiQuality(realAqiQuality);
        realTimeData.setAqi(realAqi);
        realTimeData.setDescription(descrption);

//ThreeDaysData.class
        ThreeDaysData threeDaysData = new ThreeDaysData();
        String[] threeDays_weather = new String[3];
        int[] threeDays_icon = new int[3];
        int[] threeDays_aqi = new int[3];
        int[] threeDays_max = new int[3];
        int[] threeDays_min = new int[3];

        for (int i = 0;i < threeDays_weather.length;i++){
            threeDays_weather[i] = realWeather(forecast.getResult().getDaily().getSkycon().get(i).getValue());
            threeDays_aqi[i] = (int)(forecast.getResult().getDaily().getAqi().get(i).getAvg()+0.5);
            threeDays_max[i] = (int)(forecast.getResult().getDaily().getTemperature().get(i).getMax() + 0.5);
            threeDays_min[i] = (int)(forecast.getResult().getDaily().getTemperature().get(i).getMin()+0.5);
            threeDays_icon[i] = skyconThreeDays(forecast.getResult().getDaily().getSkycon().get(i).getValue());
        }
        threeDaysData.setWeather(threeDays_weather);
        threeDaysData.setIcon(threeDays_icon);
        threeDaysData.setAqiQuality(aqiLevel(threeDays_aqi));
        threeDaysData.setMaxT(threeDays_max);
        threeDaysData.setMinT(threeDays_min);

//SunViewData.class
        SunViewData sunViewData = new SunViewData();
        String humidityS = (int)(forecast.getResult().getDaily().getHumidity().get(0).getAvg() * 100) + "%";
        String temperatureS = new DecimalFormat("0.00").format(forecast.getResult().getDaily().getTemperature().get(0).getAvg()) + "℃";
        String pressureS = new DecimalFormat("0.0").format(forecast.getResult().getDaily().getPres().get(0).getAvg() / 100) + "mb";
        Date date = forecast.getResult().getDaily().getAstro().get(0).getDate();
        String sunRiseS = forecast.getResult().getDaily().getAstro().get(0).getSunrise().getTime();
        String sunSetS = forecast.getResult().getDaily().getAstro().get(0).getSunset().getTime();
        long sunRiseL = time(new SimpleDateFormat("yyyy-MM-dd").format(date) + " " + sunRiseS);
        long sunSetL = time(new SimpleDateFormat("yyyy-MM-dd").format(date) + " " + sunSetS);
        sunViewData.setWindDirect(windDirectionS(realWindDirection));
        sunViewData.setWindLevel(windSpeedLevel(windSpeedforecast)[1]);
        sunViewData.setHumidity(humidityS);
        sunViewData.setTemperature(temperatureS);
        sunViewData.setPressure(pressureS);
        sunViewData.setSunRiseS(sunRiseS);
        sunViewData.setSunSetS(sunSetS);
        sunViewData.setSunRiseL(sunRiseL);
        sunViewData.setSunSetL(sunSetL);

//      向主线程返还解析后数据
        viewDatas.setRealTimeData(realTimeData);
        viewDatas.setThreeDaysData(threeDaysData);
        viewDatas.setChatViewData(chatViewData);
        viewDatas.setSunViewData(sunViewData);
        Message msg = mHandler.obtainMessage();
        msg.what = 4;
        msg.obj = viewDatas;
        mHandler.sendMessage(msg);*/