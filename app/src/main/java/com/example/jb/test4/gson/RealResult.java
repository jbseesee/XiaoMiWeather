/**
  * Copyright 2018 bejson.com 
  */
package com.example.jb.test4.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Auto-generated: 2018-05-07 17:12:4
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RealResult {

    private String status;
    private double o3;
    private double co;
    private double temperature;
    private double pm10;
    private String skycon;
    private double cloudrate;
    private int aqi;
    private double no2;
    private double humidity;
    private double pres;
    private double pm25;
    private double so2;
    @SerializedName("precipitation")
    private RealPrecipitation precipitation;
    @SerializedName("wind")
    private RealWind wind;
    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setO3(double o3) {
         this.o3 = o3;
     }
     public double getO3() {
         return o3;
     }

    public void setCo(double co) {
         this.co = co;
     }
     public double getCo() {
         return co;
     }

    public void setTemperature(double temperature) {
         this.temperature = temperature;
     }
     public double getTemperature() {
         return temperature;
     }

    public void setPm10(double pm10) {
         this.pm10 = pm10;
     }
     public double getPm10() {
         return pm10;
     }

    public void setSkycon(String skycon) {
         this.skycon = skycon;
     }
     public String getSkycon() {
         return skycon;
     }

    public void setCloudrate(double cloudrate) {
         this.cloudrate = cloudrate;
     }
     public double getCloudrate() {
         return cloudrate;
     }

    public void setAqi(int aqi) {
         this.aqi = aqi;
     }
     public int getAqi() {
         return aqi;
     }

    public void setNo2(int no2) {
         this.no2 = no2;
     }
     public double getNo2() {
         return no2;
     }

    public void setHumidity(double humidity) {
         this.humidity = humidity;
     }
     public double getHumidity() {
         return humidity;
     }

    public void setPres(double pres) {
         this.pres = pres;
     }
     public double getPres() {
         return pres;
     }

    public void setPm25(double pm25) {
         this.pm25 = pm25;
     }
     public double getPm25() {
         return pm25;
     }

    public void setSo2(double so2) {
         this.so2 = so2;
     }
     public double getSo2() {
         return so2;
     }

    public void setPrecipitation(RealPrecipitation precipitation) {
         this.precipitation = precipitation;
     }
     public RealPrecipitation getPrecipitation() {
         return precipitation;
     }

    public void setWind(RealWind wind) {
         this.wind = wind;
     }
     public RealWind getWind() {
         return wind;
     }

}