package com.example.jb.test4.ui.homePage;

import android.support.v4.app.Fragment;

import com.example.jb.test4.bean.CityInfo;
import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.base.BasePresenter;
import com.example.jb.test4.base.BaseView;
import com.example.jb.test4.db.City;

import java.util.List;

public interface HomePageContract {


    interface ActivityView extends BaseView<ActivityPresenter>{

        void loadCircleindicator(int position);

        void startAddCityActivity();

        void startCityManagementActivity();

        void initViewPager(List<HomePageFragment> fragments);

        void notifyDataSetChanged();

        void setCurrentItem(int position);

        void removePointOfPageIndication(int position);

        void removeIndication();


    }

    interface ActivityPresenter extends BasePresenter{
        void loadFragment();

        void addFragmentToPager(HomeViewData homeViewData);

        void deleteFragment(long[] hasDeleteId);

        void clear();

        City getCity(int index);

        void startActivity();

        void restoreFragments(List<Fragment> fragments);
    }

    interface View extends BaseView<Presenter>{

        void showWeather(HomeViewData data);

        void refreshAnimation();

        void endRefreshAnimation();

        void showErrorMessage(String message);


        long getCityId();

    }

    interface Presenter extends BasePresenter{

        void loadWeather();

        void loadWeather(CityInfo cityInfo);

        void updateWeather(long id);
    }
}
