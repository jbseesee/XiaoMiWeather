package com.example.jb.test4.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherUtil {

    public static String[] windSpeedLevel(double[] windSpeedData) {
        String[] windSpeedLevel = new String[windSpeedData.length];
        for (int i = 0; i < windSpeedData.length; i++) {
            if (windSpeedData[i] < 1) {
                windSpeedLevel[i] = "0级";
            }
            if (windSpeedData[i] >= 1 && windSpeedData[i] < 6) {
                windSpeedLevel[i] = "1级";
            }
            if (windSpeedData[i] >= 6 && windSpeedData[i] < 12) {
                windSpeedLevel[i] = "2级";
            }
            if (windSpeedData[i] >= 12 && windSpeedData[i] < 20) {
                windSpeedLevel[i] = "3级";
            }
            if (windSpeedData[i] >= 20 && windSpeedData[i] < 29) {
                windSpeedLevel[i] = "4级";
            }
            if (windSpeedData[i] >= 29 && windSpeedData[i] < 39) {
                windSpeedLevel[i] = "5级";
            }
            if (windSpeedData[i] >= 39 && windSpeedData[i] < 50) {
                windSpeedLevel[i] = "6级";
            }
            if (windSpeedData[i] >= 50 && windSpeedData[i] < 62) {
                windSpeedLevel[i] = "7级";
            }
            if (windSpeedData[i] >= 62 && windSpeedData[i] < 75) {
                windSpeedLevel[i] = "8级";
            }
            if (windSpeedData[i] >= 75 && windSpeedData[i] < 89) {
                windSpeedLevel[i] = "9级";
            }
            if (windSpeedData[i] >= 89 && windSpeedData[i] < 103) {
                windSpeedLevel[i] = "10级";
            }
            if (windSpeedData[i] >= 103 && windSpeedData[i] < 117) {
                windSpeedLevel[i] = "11级";
            }
            if (windSpeedData[i] >= 117 && windSpeedData[i] < 134) {
                windSpeedLevel[i] = "12级";
            }
            if (windSpeedData[i] >= 134 && windSpeedData[i] < 150) {
                windSpeedLevel[i] = "13级";
            }
            if (windSpeedData[i] >= 150 && windSpeedData[i] < 167) {
                windSpeedLevel[i] = "14级";
            }
            if (windSpeedData[i] >= 167 && windSpeedData[i] < 184) {
                windSpeedLevel[i] = "15级";
            }
            if (windSpeedData[i] >= 184 && windSpeedData[i] < 202) {
                windSpeedLevel[i] = "16级";
            }
            if (windSpeedData[i] >= 202 && windSpeedData[i] <= 220) {
                windSpeedLevel[i] = "17级";
            }

        }
        return windSpeedLevel;
    }



    public static String windDirectionS(double windDirection) {
        String windD = "";
        if (windDirection >= 337.5 || windDirection <= 22.5) {
            windD = "北风";
        }
        if (windDirection >= 22.5 && windDirection <= 67.5) {
            windD = "东北风";
        }
        if (windDirection >= 67.5 && windDirection <= 112.5) {
            windD = "东风";
        }
        if (windDirection >= 112.5 && windDirection <= 157.5) {
            windD = "东南风";
        }
        if (windDirection >= 157.5 && windDirection <= 202.5) {
            windD = "南风";
        }
        if (windDirection >= 202.5 && windDirection <= 247.5) {
            windD = "西南风";
        }
        if (windDirection >= 247.5 && windDirection <= 292.5) {
            windD = "西风";
        }
        if (windDirection >= 292.5 && windDirection <= 337.5) {
            windD = "西北风";
        }

        return windD;
    }

    public static String[] aqiLevel(int[] aqiData) {
        String[] aqiLevel = new String[aqiData.length];
        for (int i = 0; i < aqiData.length; i++) {
            if (aqiData[i] >= 0 && aqiData[i] < 51) {
                aqiLevel[i] = "优";
            }
            if (aqiData[i] >= 51 && aqiData[i] < 101) {
                aqiLevel[i] = "良";
            }
            if (aqiData[i] >= 101 && aqiData[i] < 151) {
                aqiLevel[i] = "轻度";
            }
            if (aqiData[i] >= 151 && aqiData[i] < 201) {
                aqiLevel[i] = "中度";
            }
            if (aqiData[i] >= 201 && aqiData[i] <= 300) {
                aqiLevel[i] = "重度";
            }
            if (aqiData[i] > 300) {
                aqiLevel[i] = "严重";
            }
        }
        return aqiLevel;
    }


    public static String aqiQuality(int aqi){
        String aqiQuality = null;
        if (aqi >= 0 && aqi <= 50) {
            aqiQuality = "空气优";
        }
        if (aqi >= 51 && aqi <= 100) {
            aqiQuality = "空气良";
        }
        if (aqi >= 101 && aqi <= 150) {
            aqiQuality = "轻度污染";
        }
        if (aqi >= 151 && aqi <= 200) {
            aqiQuality = "中度污染";
        }
        if (aqi >= 201 && aqi <= 300) {
            aqiQuality = "重度污染";
        }
        if (aqi > 300) {
            aqiQuality = "严重污染";
        }
        return aqiQuality;
    }
    public static int[] aqiColor(int[] aqiData) {
        int[] aqiColor = new int[aqiData.length];
        for (int i = 0; i < aqiData.length; i++) {
            if (aqiData[i] >= 0 && aqiData[i] <= 50) {
                aqiColor[i] = (0xff6cc615);
            }
            if (aqiData[i] >= 51 && aqiData[i] <= 100) {
                aqiColor[i] = (0xffdebf13);
            }
            if (aqiData[i] >= 101 && aqiData[i] <= 150) {
                aqiColor[i] = (0xffe68c17);
            }
            if (aqiData[i] >= 151 && aqiData[i] <= 200) {
                aqiColor[i] = (0xffe3615a);
            }
            if (aqiData[i] >= 201 && aqiData[i] <= 300) {
                aqiColor[i] = (0xff977adf);
            }
            if (aqiData[i] > 300) {
                aqiColor[i] = (0xff8f4d7c);
            }
            if (i == 0 && aqiData.length != 1) {
                aqiColor[i] = (0xffdbdbdb);
            }

        }
        return aqiColor;
    }

    public static String[] date(String[] timeHHmm, String[] timeMMdd) {
        String[] date = new String[timeHHmm.length + 1];
        date[0] = timeHHmm[0];
        date[1] = "现在";
        for (int i = 2; i < date.length; i++) {

            if (!(timeMMdd[i - 1].equals(timeMMdd[i - 2]))) {
                date[i] = timeMMdd[i - 1];

            } else {
                date[i] = timeHHmm[i - 1];
            }
        }

        return date;
    }
    public static String weatherText(String realWeather){
        String weather = realWeather;
        if (weather.equals("CLEAR_DAY") || weather.equals("CLEAR_NIGHT")){
            weather = "晴";
        }
        if (weather.equals("PARTLY_CLOUDY_DAY") || weather.equals("PARTLY_CLOUDY_NIGHT") || weather.equals("CLOUDY")){
            weather = "多云";
        }
        if (weather.equals("WIND") ){
            weather = "大风";
        }
        if (weather.equals("RAIN")){
            weather = "雨";
        }
        if (weather.equals("SNOW")){
            weather = "雪";
        }
        if (weather.equals("HAZE")){
            weather = "雾霾";
        }
        return weather;
    }



    public static long time(String time){
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date =simpleDateFormat.parse(time);
            return date.getTime();
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    public static String timeTransfromW(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String timeW = sdf.format(date);
        return timeW;
    }

    public static String timeTransfromD(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
        String timeD = sdf.format(date);
        return timeD;
    }
}
