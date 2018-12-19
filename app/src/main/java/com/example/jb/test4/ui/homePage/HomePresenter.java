package com.example.jb.test4.ui.homePage;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.jb.test4.bean.CityInfo;
import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.Model.WeatherDataRepository;
import com.example.jb.test4.util.Util;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomePresenter implements HomePageContract.Presenter {

    private HomePageContract.View mView;

    private CompositeDisposable compositeDisposable;
    private static final long ADD = -1;
    private Activity activity;

    public HomePresenter(Context context, HomePageContract.View view){
        mView = view;
        mView.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
        activity = (Activity)context;
    }

    @Override
    public void loadWeather() {
        if (Util.hasLocatePermission(activity)&&Util.isNetworkConnected()) {

            Disposable disposable = WeatherDataRepository.getInstance()
                    .locateAndGetWeather(ADD)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HomeViewData>() {
                        @Override
                        public void accept(HomeViewData homeViewData) throws Exception {
                            if (homeViewData != null) {
                                mView.showWeather(homeViewData);
                            } else {
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

        }else {
            mView.endRefreshAnimation();
        }
    }

    @Override
    public void loadWeather(CityInfo cityInfo) {
        if (Util.isNetworkConnected()) {

            Disposable disposable = WeatherDataRepository.getInstance()
                    .getWeather(cityInfo)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HomeViewData>() {
                        @Override
                        public void accept(HomeViewData homeViewData) throws Exception {
                            mView.showWeather(homeViewData);
                        }
                    });
            compositeDisposable.add(disposable);
        }else {
            mView.endRefreshAnimation();
        }

    }

    @Override
    public void updateWeather(long id) {
        if (Util.isNetworkConnected()) {

            mView.refreshAnimation();
            Disposable disposable = WeatherDataRepository.getInstance()
                    .getWeather(id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HomeViewData>() {
                        @Override
                        public void accept(HomeViewData homeViewData) throws Exception {
                            mView.showWeather(homeViewData);
                            mView.endRefreshAnimation();
                        }
                    });
            compositeDisposable.add(disposable);

        }else {
            mView.endRefreshAnimation();
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
