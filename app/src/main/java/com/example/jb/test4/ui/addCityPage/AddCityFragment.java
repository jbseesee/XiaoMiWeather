package com.example.jb.test4.ui.addCityPage;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jb.test4.bean.CityInfo;
import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.MyApplication;
import com.example.jb.test4.widgets.MyLayout;
import com.example.jb.test4.db.PopularCity;
import com.example.jb.test4.R;
import com.example.jb.test4.base.BaseFragment;
import com.example.jb.test4.db.CityList;
import com.example.jb.test4.ui.homePage.HomePageActivity;

import java.util.ArrayList;
import java.util.List;

public class AddCityFragment extends BaseFragment implements AddCityPageContract.View{

    private MyLayout myLayout;
    private TextView textView;
    private ListView listView;
    private ListViewAdapter adapter;
    private List<CityList> resultLists;
    private int c = 0;
    private Context mContext;

    private AddCityPageContract.Presenter presenter;


    @Override
    protected int getLayoutId() {
        return R.layout.add_city_fragment;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        mContext = getContext();
        textView = findView(R.id.add_city_popularcity_tv);
        myLayout = findView(R.id.popularCities_layout);
        listView = findView(R.id.add_city_searchresult_lv);
        resultLists = new ArrayList<>();
        adapter = new ListViewAdapter(mContext,R.layout.listview_searchresult_item, resultLists);
        listView.setAdapter(adapter);

        EditText editText = findView(R.id.add_city_et);
        editText.clearFocus();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String name = s.toString();
                if (name.equals("")){
                    textView.setVisibility(View.VISIBLE);
                    myLayout.setVisibility(View.VISIBLE);
                    resultLists.clear();
                }else {
                    presenter.loadList(name);
                    textView.setVisibility(View.GONE);
                    myLayout.setVisibility(View.GONE);
                }
            }
        });


        presenter.loadItem();
    }

    @Override
    public void setPresenter(AddCityPageContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void showItem(List<PopularCity> popularCityList) {
        MyLayoutAdapter myLayoutAdapter = new MyLayoutAdapter(MyApplication.getContext(),
                R.layout.popularcities_item,popularCityList);
        myLayout.setAdapter(myLayoutAdapter);
        myLayout.setOnItemClickListener(new MyLayoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {

                PopularCity popularCity = popularCityList.get(position);
                //是否点击定位按钮
                if(popularCity.isAdded()){
                    showErrorMessage("该城市已经添加!");
                }else if (popularCity.getCity().equals("定位")){
                    presenter.loadWeather();
                }
                else {
                    CityInfo cityInfo = new CityInfo();
                    cityInfo.setStreet(" ");
                    cityInfo.setDistrict(popularCity.getCity());
                    cityInfo.setCity(popularCity.getCity());
                    cityInfo.setProvince(popularCity.getProvince());
                    cityInfo.setLocation(popularCity.getLocation());
                    presenter.loadWeather(cityInfo);
                }
            }
        });
    }

    @Override
    public void showList(List<CityList> resultLists) {
        this.resultLists.clear();
        this.resultLists.addAll(resultLists);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).isAdded()) {
                    showErrorMessage("该城市已经添加!");

                }else {
                    CityInfo cityInfo = new CityInfo();
                    cityInfo.setStreet(" ");
                    cityInfo.setDistrict(resultLists.get(position).getDistrict());
                    cityInfo.setCity(resultLists.get(position).getCity());
                    cityInfo.setProvince(resultLists.get(position).getProvince());
                    cityInfo.setLocation(resultLists.get(position).getLocation());
                    presenter.loadWeather(cityInfo);
                }
            }
        });
    }

    @Override
    public void addCitytoHomePage(HomeViewData data) {

        long[] hasDeleteId = getActivity().getIntent().getLongArrayExtra("hasDeleteId");
        HomePageActivity.startHomePageActivity(getActivity(),data,hasDeleteId);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }


}
