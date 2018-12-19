package com.example.jb.test4.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CityInfo  implements Parcelable {
    private String street;
    private String district;
    private String city;
    private String province;
    private String location;
    private boolean isLocation;

    public CityInfo(){

    }


    protected CityInfo(Parcel in) {
        street = in.readString();
        district = in.readString();
        city = in.readString();
        province = in.readString();
        location = in.readString();
        isLocation = in.readByte() != 0;
    }

    public static final Creator<CityInfo> CREATOR = new Creator<CityInfo>() {
        @Override
        public CityInfo createFromParcel(Parcel in) {
            return new CityInfo(in);
        }

        @Override
        public CityInfo[] newArray(int size) {
            return new CityInfo[size];
        }
    };

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
        dest.writeString(district);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(location);
        dest.writeByte((byte) (isLocation ? 1 : 0));
    }
}
