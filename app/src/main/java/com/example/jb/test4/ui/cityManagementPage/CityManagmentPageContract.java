package com.example.jb.test4.ui.cityManagementPage;

import com.example.jb.test4.base.BasePresenter;
import com.example.jb.test4.base.BaseView;
import com.example.jb.test4.db.City;

import java.util.List;

public interface CityManagmentPageContract {

    interface View extends BaseView<CityManagmentPageContract.Presenter>{

        void showCityList(List<City> cities);

    }

    interface Presenter extends BasePresenter{

        void loadList();

        void clear(List<Long> citiesId);

    }
}
