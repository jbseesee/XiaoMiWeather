package com.example.jb.test4.ui.addCityPage;

import android.app.Activity;
import android.content.Context;

import com.example.jb.test4.bean.CityInfo;
import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.Model.WeatherDataRepository;
import com.example.jb.test4.util.Util;
import com.example.jb.test4.db.CityList;
import com.example.jb.test4.db.PopularCity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AddCityPresenter implements AddCityPageContract.Presenter {

    private AddCityPageContract.View mView;
    private static final long ADD = -1;


    private CompositeDisposable compositeDisposable;

    private Activity activity;



    public AddCityPresenter(Context context,AddCityPageContract.View mView){
        this.mView = mView;
        this.mView.setPresenter(this);
        activity = (Activity) context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadItem() {
        List<PopularCity> popularCityList =  WeatherDataRepository.getInstance().getPopularCities();
        mView.showItem(popularCityList);
    }

    @Override
    public void loadList(String name) {
        List<CityList> cityLists = WeatherDataRepository.getInstance().queryFromCityList(name);
        mView.showList(cityLists);
    }
    @Override
    public void loadWeather() {
        if (Util.hasLocatePermission(activity)&&Util.isNetworkConnected()){
            Disposable disposable = WeatherDataRepository.getInstance()
                    .locateAndGetWeather(ADD)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HomeViewData>() {
                        @Override
                        public void accept(HomeViewData homeViewData) throws Exception {
                            if (homeViewData != null) {
                                mView.addCitytoHomePage(homeViewData);
                            }else {
                                mView.showErrorMessage("服务器无数据返回！");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            mView.showErrorMessage(throwable.getMessage());
                        }
                    });
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void loadWeather(CityInfo cityInfo) {
        if (Util.isNetworkConnected()) {

            if (WeatherDataRepository.getInstance().hasExist(cityInfo.getDistrict(), cityInfo.getCity())) {
                mView.showErrorMessage("该城市已经添加");
            } else {
                Disposable disposable = WeatherDataRepository.getInstance()
                        .getWeather(cityInfo)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<HomeViewData>() {
                            @Override
                            public void accept(HomeViewData homeViewData) throws Exception {
                                mView.addCitytoHomePage(homeViewData);
                            }
                        });
                compositeDisposable.add(disposable);
            }
        }

    }


    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
