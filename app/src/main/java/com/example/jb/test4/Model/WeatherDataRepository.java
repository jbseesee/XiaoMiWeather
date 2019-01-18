package com.example.jb.test4.Model;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.example.jb.test4.bean.CityInfo;
import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.Model.retrofit.HttpUtil;
import com.example.jb.test4.db.PopularCity;
import com.example.jb.test4.util.Util;
import com.example.jb.test4.bean.WeatherData;
import com.example.jb.test4.db.City;
import com.example.jb.test4.db.CityList;
import com.example.jb.test4.gson.Forecast;
import com.example.jb.test4.gson.Realtime;
import com.example.jb.test4.util.WeatherUtil;

import org.litepal.LitePal;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class WeatherDataRepository implements WeatherDataSoucre<City> {

    private static WeatherDataRepository mInstance;

    private Observable observable;

    private HomeViewData homeViewData;
    private WeatherData weatherData;


    private WeatherDataRepository() {

    }

    public static synchronized WeatherDataRepository getInstance() {
        if (mInstance == null) {
            mInstance = new WeatherDataRepository();
        }
        return mInstance;
    }

    @Override
    public Observable<HomeViewData> locateAndGetWeather(long type) {
        CityInfo cityInfo = new CityInfo();
        observable = Location.getInstance().start()
                .observeOn(Schedulers.io())
                .flatMap(new Function<AMapLocation, ObservableSource<WeatherData>>() {
                    @Override
                    public ObservableSource<WeatherData> apply(AMapLocation aMapLocation) throws Exception {

                        if (aMapLocation.getErrorCode() == 0) {
                            String location = "";
                            location = Util.handleLocation(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            cityInfo.setLocation(true);
                            cityInfo.setStreet(aMapLocation.getStreet());
                            cityInfo.setDistrict(aMapLocation.getDistrict());
                            cityInfo.setCity(Util.handleString(aMapLocation.getCity()));
                            cityInfo.setProvince(Util.handleString(aMapLocation.getProvince()));
                            cityInfo.setLocation(location);
                            return HttpUtil.getmInstance().creat(location);
                        } else {
                            return Observable.error(new Throwable("定位失败！"));
                        }
                    }
                })
                .observeOn(Schedulers.computation())
                .map(new Function<WeatherData, HomeViewData>() {
                    @Override
                    public HomeViewData apply(WeatherData weatherData) {
                        if (weatherData!=null) {
                            weatherData.setCityInfo(cityInfo);
                            Schedulers.io().createWorker().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    saveDb(weatherData, type);
                                }
                            });
                            return new HandleJson().handleData(weatherData);
                        }else {
                            return null;
                        }
                    }
                });

        return observable;

    }

    @Override
    public Observable<HomeViewData> getWeather(long id) {
        City city = LitePal.find(City.class,id);
        CityInfo cityInfo = new CityInfo();
        if (city.isLocation()){//需要定位
            observable = locateAndGetWeather(LocateType.update(id));

        }else {
            observable = HttpUtil.getmInstance().creat(city.getLocation())
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(new Function<WeatherData, HomeViewData>() {
                        @Override
                        public HomeViewData apply(WeatherData weatherData) {
                            if (weatherData!=null) {
                                cityInfo.setStreet(city.getStreet());
                                cityInfo.setDistrict(city.getDistrict());
                                cityInfo.setCity(Util.handleString(city.getCity()));
                                cityInfo.setProvince(Util.handleString(city.getProvince()));
                                cityInfo.setLocation(city.getLocation());
                                weatherData.setCityInfo(cityInfo);
                                Schedulers.io().createWorker().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        saveDb(weatherData, id);
                                    }
                                });
                                return new HandleJson().handleData(weatherData);
                            }else {
                                return null;
                            }
                        }
                    });
        }
        return observable;
    }

    @Override
    public Observable<HomeViewData> getWeather(CityInfo cityInfo2) {
        CityInfo cityInfo = cityInfo2;
        observable = HttpUtil.getmInstance().creat(cityInfo.getLocation())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(new Function<WeatherData, HomeViewData>() {
                    @Override
                    public HomeViewData apply(WeatherData weatherData) {
                        if (weatherData!=null) {
                            weatherData.setCityInfo(cityInfo);
                            Schedulers.io().createWorker().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    saveDb(weatherData, LocateType.ADD);
                                }
                            });
                            return new HandleJson().handleData(weatherData);
                        }else {
                            return null;
                        }
                    }
                });
        return observable;
    }

    @Override
    public void create(City city) {
        city.save();
    }


    @Override
    public void delete(long id) {
        LitePal.delete(City.class, id);

    }

    public boolean hasExist(String district,String cityName){
        List<City> cities = LitePal.findAll(City.class);
        for (City city: cities){
            if (district.equals(city.getDistrict()) && cityName.equals(city.getCity())){
                return true;
            }
        }
        return false;
    }


    public List<CityList> queryFromCityList(String name){

        List<City> cities = LitePal.findAll(City.class);
        List<CityList> cityLists = LitePal.where("district LIKE ? OR city LIKE ?",
                name + "%",name + "%").find(CityList.class);
        if (cities.isEmpty()){
            return cityLists;
        }
        for (City city:cities) {
            for (CityList cityList:cityLists) {
                if (city.getDistrict().equals(cityList.getDistrict())){
                    cityList.setAdded(true);
                    break;
                }else if (city.isLocation() && city.getCity().equals(cityList.getDistrict())){
                    cityList.setAdded(true);
                    break;
                }
            }
        }

        return cityLists;
    }

    public List<PopularCity> getPopularCities(){
        List<City> cities = LitePal.findAll(City.class);
        List<PopularCity> popularCities = LitePal.findAll(PopularCity.class);
        if (cities.isEmpty()){
            return popularCities;
        }

        for (City city : cities){
            for (PopularCity popularCity: popularCities){
                if (city.getDistrict().equals(popularCity.getCity())){
                    popularCity.setAdded(true);
                    break;
                }else if (city.isLocation()&&popularCity.getCity().equals("定位")) {
                    popularCity.setAdded(true);
                    break;
                }

            }
        }
        return popularCities;
    }


    void saveDb(WeatherData weatherData,long id){
        CityInfo cityInfo = weatherData.getCityInfo();
        Realtime realtime = weatherData.getRealtime();
        Forecast forecast = weatherData.getForecast();
        int temperture = (int) (realtime.getResult().getTemperature() + 0.5);
        int maxTm = (int) (forecast.getResult().getDaily().getTemperature().get(0).getMax() + 0.5);
        int minTm = (int) (forecast.getResult().getDaily().getTemperature().get(0).getMin() + 0.5);
        String weather = forecast.getResult().getDaily().getSkycon().get(0).getValue();
        String aqiQuality = WeatherUtil.aqiQuality((int) (forecast.getResult().getDaily().getAqi().get(0).getAvg()));
        String windDirect = WeatherUtil.windDirectionS(forecast.getResult().getDaily().getWindD().get(0).getAvg().getDirection());
        double[] windlevel = {forecast.getResult().getDaily().getWindD().get(0).getAvg().getSpeed()};
        String windLevel = WeatherUtil.windSpeedLevel(windlevel)[0];
        String humidity = (int) (forecast.getResult().getDaily().getHumidity().get(0).getAvg() * 100) + "%";
        long updateTime = realtime.getServer_time();

        City city;
        long cityId = id;
        if (id == -1) {
            city = new City();
            city.save();
            cityId = city.getId();
        }else {
            city = LitePal.find(City.class,cityId);
        }
        if(cityInfo.isLocation() || id == -1){
            city.setLocation(cityInfo.getLocation());
            city.setStreet(cityInfo.getStreet());
            city.setDistrict(cityInfo.getDistrict());
            city.setCity(cityInfo.getCity());
            city.setProvince(cityInfo.getProvince());
            city.setLocation(cityInfo.isLocation());
        }

        city.setTemperture(temperture);
        city.setMaxTm(maxTm);
        city.setMinTm(minTm);
        city.setWeatherText(weather);
        city.setAqiQuality(aqiQuality);
        city.setWindDirect(windDirect);
        city.setWindLevel(windLevel);
        city.setHumidity(humidity);
        city.setUpdateTime(updateTime);
        city.update(cityId);
    }





}
class LocateType{
    public static final long ADD = -1;
    public static long update(long id) {
        return id;
    }
}
