package com.example.jb.test4.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Realtime {

    private String status;
    private String lang;
    private long server_time;
    private int tzshift;
    private List<Double> location;
    private String unit;
    @SerializedName("result")
    private RealResult result;
    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setLang(String lang) {
         this.lang = lang;
     }
     public String getLang() {
         return lang;
     }

    public void setServer_time(long server_time) {
         this.server_time = server_time;
     }
     public long getServer_time() {
         return server_time;
     }

    public void setTzshift(int tzshift) {
         this.tzshift = tzshift;
     }
     public int getTzshift() {
         return tzshift;
     }

    public void setLocation(List<Double> location) {
         this.location = location;
     }
     public List<Double> getLocation() {
         return location;
     }

    public void setUnit(String unit) {
         this.unit = unit;
     }
     public String getUnit() {
         return unit;
     }

    public void setResult(RealResult result) {
         this.result = result;
     }
     public RealResult getResult() {
         return result;
     }

}