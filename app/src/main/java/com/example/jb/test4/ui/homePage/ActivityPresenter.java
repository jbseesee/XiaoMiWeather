package com.example.jb.test4.ui.homePage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.db.City;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ActivityPresenter implements HomePageContract.ActivityPresenter {

    private HomePageContract.ActivityView mView;

    private List<City> cityList;
    private List<HomePageFragment> fragments;
    private List<HomePresenter> homePresenters;
    private Context context;

    public ActivityPresenter(Context context, HomePageContract.ActivityView mView){
        this.context = context;
        this.mView = mView;
        this.mView.setPresenter(this);
        fragments = new ArrayList<>();
        homePresenters = new ArrayList<>();
        cityList = new ArrayList<>();
    }

    @Override
    public void loadFragment(){
       cityList = LitePal.findAll(City.class);
       int size = cityList.size();
       if (cityList.size()>0){
           for (int i = 0; i < size; i++){
               creatFragment(cityList.get(i).getId());
               mView.loadCircleindicator(i);
           }
           mView.notifyDataSetChanged();
       }else {
           mView.startAddCityActivity();
       }
    }

    @Override
    public void addFragmentToPager(HomeViewData homeViewData){
        City city = LitePal.findLast(City.class);
        cityList.add(city);
        creatFragment(city.getId());
        fragments.get(cityList.size()-1).setData(homeViewData);
        mView.notifyDataSetChanged();
        mView.loadCircleindicator(cityList.size() - 1);
        mView.setCurrentItem(cityList.size() - 1);
    }

    @Override
    public void deleteFragment(long[] hasDeleteId){

        //逐个删除id对应的元素
        for (long id: hasDeleteId){
            for (int i = 0; i < fragments.size(); i++){
                if (fragments.get(i).getCityId()==id){
                    int position = fragments.indexOf(fragments.get(i));
                    homePresenters.remove(position);
                    cityList.remove(position);
                    mView.removePointOfPageIndication(position);
                    fragments.remove(position);
                    Log.d("test","该位置地区" + position + "天气信息已被移除!");
                    break;
                }
            }
        }
    }

    @Override
    public void restoreFragments(List<Fragment> fragments) {
        cityList = LitePal.findAll(City.class);
        int size = cityList.size();
        int size2 = fragments.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size2; j++) {
                long id = fragments.get(j).getArguments().getLong("id");
                if (cityList.get(i).getId() == id) {
                    HomePageFragment homePageFragment = (HomePageFragment) fragments.get(j);
                    HomePresenter homePresenter = new HomePresenter(context, homePageFragment);
                    this.fragments.add(homePageFragment);
                    homePresenters.add(homePresenter);
                    mView.loadCircleindicator(i);
                    break;

                }else if (j == size2-1){
                    creatFragment(cityList.get(i).getId());
                    mView.loadCircleindicator(i);

                } else {
                    continue;
                }
            }

        }
        mView.notifyDataSetChanged();

    }

    @Override
    public void clear(){
        homePresenters.clear();
        cityList.clear();
        mView.removeIndication();
        fragments.clear();
    }

    @Override
    public City getCity(int index) {
        return cityList.get(index);
    }

    @Override
    public void startActivity() {
        //当前城市数量小于2，启动添加城市页面，反之启动城市管理页面

        int citiesCount = LitePal.count(City.class);
        if (citiesCount>= 2){
            mView.startCityManagementActivity();
        }else {
            mView.startAddCityActivity();
        }
    }

    //创建viewpager的Fragment
    private void creatFragment(long id){
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        HomePageFragment newFragment = new HomePageFragment();
        newFragment.setArguments(bundle);
        HomePresenter homePresenter = new HomePresenter(context,newFragment);
        fragments.add(newFragment);
        homePresenters.add(homePresenter);
    }

    @Override
    public void subscribe() {
        mView.initViewPager(fragments);
    }

    @Override
    public void unsubscribe() {

    }
}
