package com.example.jb.test4.ui.addCityPage;

import com.example.jb.test4.bean.CityInfo;
import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.base.BasePresenter;
import com.example.jb.test4.base.BaseView;
import com.example.jb.test4.db.CityList;
import com.example.jb.test4.db.PopularCity;

import java.util.List;

public interface AddCityPageContract {

    interface View extends BaseView<AddCityPageContract.Presenter>{

        void showItem(List<PopularCity> popularCityList);

        void showList(List<CityList> list);

        void addCitytoHomePage(HomeViewData data);

        void showErrorMessage(String message);

    }

    interface Presenter extends BasePresenter{

        void loadItem();

        void loadList(String name);

        void loadWeather();

        void loadWeather(CityInfo cityInfo);

    }
}
