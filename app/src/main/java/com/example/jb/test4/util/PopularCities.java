package com.example.jb.test4.util;

public class PopularCities {
    private String[] popularCities = {"定位","北京","上海","广州","深圳","珠海","佛山","南京","苏州","厦门",
            "长沙","成都","福州","杭州","武汉","青岛","西安","太原","石家庄","沈阳","重庆","天津","南宁"};

    private String[] cityLocation = {"0",
            "116.4273,39.9025",
            "121.4557,31.2500",
            "113.2570,23.1507",
            "114.1173,22.5318",
            "113.5492,22.2150",
            "113.1037,23.0469",
            "118.7968,32.0867",
            "120.6109,31.3291",
            "118.0741,24.6362",
            "113.0148,28.1994",
            "104.0740,30.6975",
            "119.3199,26.1136",
            "120.1829,30.2435",
            "114.4243,30.6075",
            "120.3126,36.0636",
            "108.9628,34.2779",
            "112.5880,37.8606",
            "114.4867,38.0214",
            "123.3967,41.7983",
            "106.5477,29.5497",
            "117.2095,39.1363",
            "108.3150,22.8273"
    };

    private String[] province = {" ","北京","上海","广东","广东","广东","广东","江苏","江苏","福建","湖南",
            "四川","福建","浙江","湖北","山东","陕西","山西","河北","辽宁","重庆","天津","广西"};

    public String[] getPopularCities() {
        return popularCities;
    }

    public void setPopularCities(String[] popularCities) {
        this.popularCities = popularCities;
    }

    public String[] getCityLocation() {
        return cityLocation;
    }

    public void setCityLocation(String[] cityLocation) {
        this.cityLocation = cityLocation;
    }

    public String[] getProvince() {
        return province;
    }

    public void setProvince(String[] province) {
        this.province = province;
    }
}
