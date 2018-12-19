package com.example.jb.test4.util;

import com.example.jb.test4.R;

public class LoadIconUtil {

    public static int windDirection(double windDirectionData) {
        int windDirection = 0;
        if (windDirectionData >= 337.5 || windDirectionData <= 22.5) {
            windDirection = R.drawable.wind_north;
        }
        if (windDirectionData >= 22.5 && windDirectionData <= 67.5) {
            windDirection = R.drawable.wind_northeast;
        }
        if (windDirectionData >= 67.5 && windDirectionData <= 112.5) {
            windDirection = R.drawable.wind_east;
        }
        if (windDirectionData >= 112.5 && windDirectionData <= 157.5) {
            windDirection = R.drawable.wind_southeast;
        }
        if (windDirectionData >= 157.5 && windDirectionData <= 202.5) {
            windDirection = R.drawable.wind_south;
        }
        if (windDirectionData >= 202.5 && windDirectionData <= 247.5) {
            windDirection = R.drawable.wind_southwest;
        }
        if (windDirectionData >= 247.5 && windDirectionData <= 292.5) {
            windDirection = R.drawable.wind_west;
        }
        if (windDirectionData >= 292.5 && windDirectionData <= 337.5) {
            windDirection = R.drawable.wind_northwest;
        }

        return windDirection;
    }

    public static int skycon(String skyconforecast){
        int icon = 0;


        if (skyconforecast.equals("CLEAR_DAY")) {
            icon = R.drawable.icon_sunny;
        }
        if (skyconforecast.equals("CLEAR_NIGHT")) {
            icon = R.drawable.icon_sunny_night;
        }
        if (skyconforecast.equals("PARTLY_CLOUDY_DAY")) {
            icon = R.drawable.icon_cloudy;
        }
        if (skyconforecast.equals("PARTLY_CLOUDY_NIGHT")) {
            icon = R.drawable.icon_cloudy_night;
        }
        if (skyconforecast.equals("CLOUDY") || skyconforecast.equals("WIND")) {
            icon = R.drawable.icon_overcast;
        }
        if (skyconforecast.equals("RAIN")) {
            icon = R.drawable.icon_light_rain;
        }
        if (skyconforecast.equals("SNOW")) {
            icon = R.drawable.icon_light_snow;
        }
        if (skyconforecast.equals("HAZE")) {
            icon = R.drawable.icon_fog;
        }

        return icon;
    }

    public static int skyconThreeDays(String skyconforecast){
        int icon = 0;

        if (skyconforecast.equals("CLEAR_DAY")){
            icon = R.drawable.daily_forecast_sunny;
        }
        if (skyconforecast.equals("CLEAR_NIGHT")){
            icon = R.drawable.daily_forecast_sunny_night;
        }
        if (skyconforecast.equals("PARTLY_CLOUDY_DAY")){
            icon = R.drawable.daily_forecast_cloudy;
        }
        if (skyconforecast.equals("PARTLY_CLOUDY_NIGHT")){
            icon = R.drawable.daily_forecast_cloudy_night;
        }
        if (skyconforecast.equals("CLOUDY") || skyconforecast.equals("WIND") ){
            icon = R.drawable.daily_forecast_overcast;
        }
        if (skyconforecast.equals("RAIN")){
            icon = R.drawable.daily_forecast_light_rain;
        }
        if (skyconforecast.equals("SNOW")){
            icon = R.drawable.daily_forecast_light_snow;
        }
        if (skyconforecast.equals("HAZE")){
            icon = R.drawable.daily_forecast_foggy;
        }

        return icon;
    }

    public static int aqiIcon(String aqiQuality) {
        int aqiIcon = 0;
        if (aqiQuality.equals("空气优") || aqiQuality.equals("空气良")){
            aqiIcon = R.drawable.realtime_aqi_leaf;
        }
        if (aqiQuality.equals("轻度污染") || aqiQuality.equals("中度污染")){
            aqiIcon = R.drawable.realtime_aqi_skull;
        }
        if (aqiQuality.equals("重度污染") || aqiQuality.equals("严重污染")){
            aqiIcon = R.drawable.realtime_aqi_gas_mask;
        }
        return aqiIcon;
    }
}
