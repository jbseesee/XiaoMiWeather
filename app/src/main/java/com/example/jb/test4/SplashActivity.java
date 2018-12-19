package com.example.jb.test4;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jb.test4.ui.addCityPage.AddCityActivity;
import com.example.jb.test4.base.BaseActivity;
import com.example.jb.test4.db.CityList;
import com.example.jb.test4.db.PopularCity;
import com.example.jb.test4.db.SharedPreferencesUtil;
import com.example.jb.test4.ui.homePage.HomePageActivity;
import com.example.jb.test4.util.PopularCities;
import com.example.jb.test4.util.Util;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    private ImageView appIcon_iv;
    private LinearLayout loadLayout;
    private ImageView loadIcon_iv;

    private CompositeDisposable compositeDisposable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        appIcon_iv = findViewById(R.id.appIcon_iv);
        loadLayout = findViewById(R.id.loadLayout);
        loadIcon_iv =findViewById(R.id.loading_iv);

        compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadData() {
        boolean isFirst = SharedPreferencesUtil.getmInstance().getBoolean("isFirst");
        RxPermissions rxPermissions = new RxPermissions(this);
        if(Build.VERSION.SDK_INT >= 23) {
            loading();
            rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                    .observeOn(Schedulers.io())
                    .map(new Function<Boolean, String>() {
                        @Override
                        public String apply(Boolean aBoolean) {
                            if (isFirst) {
                                initData();
                                SharedPreferencesUtil.getmInstance().put("isFirst", false);
                            }
                            return "Done";
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            loadFinish();
                            chooseActivity();
                        }
                    });

        } else if (isFirst){
            loading();
            initData();
            SharedPreferencesUtil.getmInstance().put("isFirst", false);
            startAddCityActivity();

        }else {
            loadFinish();
           chooseActivity();
        }
    }

    /**
     * 写入数据到数据库
     */
    private void initData() {
        createPopularCity();
        createCityList();

    }

    /**
     * 这是一个载入动画
     */
    private void loading(){
        appIcon_iv.setVisibility(View.INVISIBLE);
        loadLayout.setVisibility(View.VISIBLE);
        Disposable disposable =  Observable.interval(0,2000,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        loadIcon_iv.animate()
                                .rotationBy(360)
                                .setInterpolator(new LinearInterpolator())
                                .setDuration(2000);
                    }
                });

        compositeDisposable.add(disposable);
    }


    /**
     * 结束载入动画
     */
    private void loadFinish(){
        compositeDisposable.clear();
        loadLayout.setVisibility(View.GONE);
        appIcon_iv.setVisibility(View.VISIBLE);
    }


    /**
     * 将csv文件的数据写入到数据库
     */
    private void createCityList(){
        InputStream inputStream;
        BufferedReader bufferedReader;
        CityList cityList;
        try {
            inputStream = SplashActivity.this.getResources().getAssets().open("cityidloc.csv");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true){
                String cou = bufferedReader.readLine();
                if (cou == null){
                    break;
                }
                String[] line = cou.split(",");
                cityList = new CityList();
                cityList.setDistrict(line[1]);
                cityList.setCity(line[2]);
                cityList.setProvince(line[3]);
                cityList.setLocation(Util.handleLocation(Double.valueOf(line[4]),Double.valueOf(line[5])));
                cityList.save();
            }
            inputStream.close();
            bufferedReader.close();
            Log.d("PROCESS!","CityListdone!!!" );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createPopularCity() {
        PopularCities popularCities = new PopularCities();
        String[] cities = popularCities.getPopularCities();
        String[] location = popularCities.getCityLocation();
        String[] province = popularCities.getProvince();
        for (int i = 0; i < cities.length; i++) {
            PopularCity popularCity = new PopularCity();
            popularCity.setCity(cities[i]);
            popularCity.setLocation(location[i]);
            popularCity.setProvince(province[i]);
            popularCity.save();
        }
        Log.d("PROCESS!","PopularCitydone!!!" );
    }


    /**
     * 延迟一段时间后载入主页
     */
    @SuppressLint("CheckResult")
    private void chooseActivity(){
        Observable.intervalRange(0,3,0,1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong == 1) {
                            startHomePageActivity();
                            finish();
                        }
                    }
                });

    }
    private void startHomePageActivity(){
        startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
        finish();
    }
    private void startAddCityActivity(){
        startActivity(new Intent(SplashActivity.this, AddCityActivity.class));
        finish();
    }

}
