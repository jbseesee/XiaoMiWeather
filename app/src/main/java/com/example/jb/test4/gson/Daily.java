/**
  * Copyright 2018 bejson.com 
  */
package com.example.jb.test4.gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Auto-generated: 2018-05-02 20:50:55
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Daily {

    private String status;
    private List<ColdRisk> coldRisk;

    @SerializedName("temperature")
    private List<TemperatureD> temperatured;
    private List<Skycon> skycon;
    @SerializedName("aqi")
    private List<AqiD> aqiD;
    private List<Humidity> humidity;
    private List<Astro> astro;
    private List<Pres> pres;
    private List<Ultraviolet> ultraviolet;
    private List<Dressing> dressing;
    private List<CarWashing> carWashing;
    @SerializedName("wind")
    private List<WindD> windD;
    private List<Skycon_08h_20h> skycon_08h_20h;
    private List<Skycon_20h_32h> skycon_20h_32h;

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setColdRisk(List<ColdRisk> coldRisk) {
         this.coldRisk = coldRisk;
     }
     public List<ColdRisk> getColdRisk() {
         return coldRisk;
     }

    public void setTemperatureD(List<TemperatureD> temperature) {
         this.temperatured = temperature;
     }
     public List<TemperatureD> getTemperature() {
         return temperatured;
     }

    public void setSkycon(List<Skycon> skycon) {
         this.skycon = skycon;
     }
     public List<Skycon> getSkycon() {
         return skycon;
     }

    public void setAqi(List<AqiD> aqi) {
         this.aqiD = aqi;
     }
     public List<AqiD> getAqi() {
         return aqiD;
     }

    public void setHumidity(List<Humidity> humidity) {
         this.humidity = humidity;
     }
     public List<Humidity> getHumidity() {
         return humidity;
     }

    public void setAstro(List<Astro> astro) {
         this.astro = astro;
     }
     public List<Astro> getAstro() {
         return astro;
     }

    public void setPres(List<Pres> pres) {
         this.pres = pres;
     }
     public List<Pres> getPres() {
         return pres;
     }

    public void setUltraviolet(List<Ultraviolet> ultraviolet) {
         this.ultraviolet = ultraviolet;
     }
     public List<Ultraviolet> getUltraviolet() {
         return ultraviolet;
     }

    public void setDressing(List<Dressing> dressing) {
         this.dressing = dressing;
     }
     public List<Dressing> getDressing() {
         return dressing;
     }

    public void setCarWashing(List<CarWashing> carWashing) {
         this.carWashing = carWashing;
     }
     public List<CarWashing> getCarWashing() {
         return carWashing;
     }


    public void setWindD(List<WindD> windD) {
         this.windD = windD;
     }
     public List<WindD> getWindD() {
         return windD;
     }

    public List<Skycon_08h_20h> getSkycon_08h_20h() {
        return skycon_08h_20h;
    }

    public void setSkycon_08h_20h(List<Skycon_08h_20h> skycon_08h_20h) {
        this.skycon_08h_20h = skycon_08h_20h;
    }

    public List<Skycon_20h_32h> getSkycon_20h_32h() {
        return skycon_20h_32h;
    }

    public void setSkycon_20h_32h(List<Skycon_20h_32h> skycon_20h_32h) {
        this.skycon_20h_32h = skycon_20h_32h;
    }
}