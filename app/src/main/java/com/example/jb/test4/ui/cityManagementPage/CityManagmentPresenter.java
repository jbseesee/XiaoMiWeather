package com.example.jb.test4.ui.cityManagementPage;

import android.content.Context;

import com.example.jb.test4.db.City;

import org.litepal.LitePal;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class CityManagmentPresenter implements CityManagmentPageContract.Presenter {

    private CityManagmentPageContract.View mView;

    private CompositeDisposable compositeDisposable;



    public CityManagmentPresenter(Context context, CityManagmentPageContract.View mView){
        this.mView = mView;
        mView.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }



    @Override
    public void subscribe() {
        loadList();
    }

    @Override
    public void unsubscribe() {
        //compositeDisposable.clear();
    }

    @Override
    public void loadList() {
        List<City> cities = LitePal.findAll(City.class);
        mView.showCityList(cities);
    }


    @Override
    public void clear(List<Long> citiesId) {
        for (Long id: citiesId){
            LitePal.delete(City.class,id);
        }
    }
}
