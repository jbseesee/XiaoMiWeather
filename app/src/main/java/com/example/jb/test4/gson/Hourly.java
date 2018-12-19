/**
  * Copyright 2018 bejson.com 
  */
package com.example.jb.test4.gson;
import java.util.List;


public class Hourly {

    private String status;
    private String description;
    private List<Skycon> skycon;
    private List<Aqi> aqi;

    private List<Wind> wind;
    private List<Temperture> temperature;
    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setSkycon(List<Skycon> skycon) {
         this.skycon = skycon;
     }
     public List<Skycon> getSkycon() {
         return skycon;
     }


    public void setAqi(List<Aqi> aqi) {
         this.aqi = aqi;
     }
     public List<Aqi> getAqi() {
         return aqi;
     }



    public void setWin(List<Wind> wind) {
         this.wind = wind;
     }
     public List<Wind> getWind() {
         return wind;
     }

    public void setTemperature(List<Temperture> temperature) {
         this.temperature = temperature;
     }
     public List<Temperture> getTemperature() {
         return temperature;
     }

}