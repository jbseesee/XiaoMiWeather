package com.example.jb.test4.ui.cityManagementPage;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.jb.test4.R;
import com.example.jb.test4.ui.addCityPage.AddCityActivity;
import com.example.jb.test4.base.BaseFragment;
import com.example.jb.test4.db.City;
import com.example.jb.test4.util.ActivityResultCodeUtil;

import java.util.ArrayList;
import java.util.List;

public class CityManagementFragment extends BaseFragment implements CityManagmentPageContract.View, View.OnClickListener{
    private static final int NORMAL = 0;
    private static final int EDIT = 1;

    private List<City> cityManageList;
    private CityManagementAdapter adapter;
    private RelativeLayout tabEdit;
    private RelativeLayout tabNormal;

    private Context mContext;


    private FloatingActionButton add;
    private RecyclerView cityList;

    private long[] hasDeleteids ;

    private CityManagmentPageContract.Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.city_management_fragment;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        mContext = getContext();
        ImageView back = findView(R.id.cityManagementBack_iv);
        ImageView edit = findView(R.id.cityManagementEdit_iv);
        add = findView(R.id.fab_add_city);
        Button cancel = findView(R.id.cityManagementCancle_bt);
        cancel.setBackgroundResource(R.drawable.button_cancel_normal);
        Button determine = findView(R.id.cityManagementDetermine_bt);
        tabEdit =findView(R.id.city_management_tab_edit);
        tabNormal = findView(R.id.city_management_tab_normal);
        cityList = findView(R.id.cities_rv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        cityList.setLayoutManager(manager);
        back.setOnClickListener(this);
        edit.setOnClickListener(this);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        determine.setOnClickListener(this);
        cityManageList = new ArrayList<>();
        hasDeleteids = new long[0];
        if (saveInstanceState!=null) {
            hasDeleteids = new long[saveInstanceState.getLongArray("hasDeleteId").length];
            hasDeleteids = saveInstanceState.getLongArray("hasDeleteId");
        }
    }


    @Override
    public void onClick(View v) {
        List<Long> cityItems = adapter.getdeleteIdTemp();
        List<Integer> positions = adapter.getDeletePositionTemp();
        List<City> listTemp = adapter.getListsTemp();
        int size = cityItems.size();
        switch (v.getId()){
            case R.id.cityManagementBack_iv:
                getActivity().finish();
                break;

            case R.id.cityManagementEdit_iv:
                tabLayoutChange(EDIT);
                adapter.setEdit();
                adapter.notifyDataSetChanged();
                break;
                
            case R.id.cityManagementCancle_bt:
            if (size != 0){
                for (int i = 1; i <= size;i++ ){
                    cityManageList.add(positions.get(size-i),listTemp.get(size-i));
                    adapter.notifyItemInserted(positions.get(size-i));
                }
            }
            tabLayoutChange(NORMAL);
            adapter.setEditCancle();
            adapter.notifyDataSetChanged();
            break;

            case R.id.cityManagementDetermine_bt:
            if (size != 0){
                presenter.clear(cityItems);
            }
            tabLayoutChange(NORMAL);
            adapter.setEditDetermine();
            adapter.notifyDataSetChanged();
            if (adapter.getListSize()==0){
                clearAll();
                getActivity().finish();
            }
            break;

            case R.id.fab_add_city:
                startAddCityActivity();
                break;
        }
    }

    private void tabLayoutChange(int mode){
        switch (mode){
            case NORMAL:
                tabEdit.setVisibility(View.GONE);
                tabNormal.setVisibility(View.VISIBLE);
                add.setVisibility(View.VISIBLE);
                break;
            case EDIT:
                tabNormal.setVisibility(View.GONE);
                tabEdit.setVisibility(View.VISIBLE);
                add.setVisibility(View.GONE);
                break;
        }
    }



    private void startAddCityActivity(){
        AddCityActivity.startAddActivity(mContext,getHasDeleteId());
    }

    private void returnToHomePageActivity(int position){
        Intent intent = new Intent();
        intent.putExtra("position",position);
        intent.putExtra("hasDeleteId",getHasDeleteId());
        getActivity().setResult(ActivityResultCodeUtil.TURN_TO,intent);
        getActivity().finish();

    }

    private void clearAll(){
        Intent intent = new Intent();
        getActivity().setResult(ActivityResultCodeUtil.CLEAR,intent);
    }




    public long[] getHasDeleteId(){
        List<Long> ids = adapter.getHasDeleteId();
        int size = ids.size();
        long[] id = new long[size];
        for (int i = 0; i < size; i++){
            id[i] = ids.get(i);
        }
        if (size!=0){
            return id;
        }else  {
            return hasDeleteids;
        }
    }

    @Override
    public void showCityList(List<City> cities) {
        if (cityManageList.size()>0){
            cityManageList.clear();
        }
        cityManageList.clear();
        cityManageList.addAll(cities);
        if (adapter==null) {
            adapter = new CityManagementAdapter(cityManageList);
        }
        cityList.setAdapter(adapter);
        adapter.setOnItemClickListener(new CityManagementAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                returnToHomePageActivity(position);
            }
        });
    }

    @Override
    public void setPresenter(CityManagmentPageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLongArray("hasDeleteId",getHasDeleteId());
        super.onSaveInstanceState(outState);
    }
}
