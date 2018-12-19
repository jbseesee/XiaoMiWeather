package com.example.jb.test4.db;

import org.litepal.crud.LitePalSupport;

public class PopularCity extends LitePalSupport{
    private String city;
    private String location;
    private String province;
    private boolean isAdded;

    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}
