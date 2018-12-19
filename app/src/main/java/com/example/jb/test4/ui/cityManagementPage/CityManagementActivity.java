package com.example.jb.test4.ui.cityManagementPage;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.example.jb.test4.R;
import com.example.jb.test4.base.BaseActivity;
import com.example.jb.test4.util.ActivityResultCodeUtil;

public class CityManagementActivity extends BaseActivity{
    private CityManagementFragment fragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_management;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        fragment = (CityManagementFragment)getSupportFragmentManager().findFragmentById(R.id.cityManagement_layout);
        if (fragment==null) {
            fragment = new CityManagementFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.cityManagement_layout, fragment, null);
            transaction.commit();
        }
        CityManagmentPresenter presenter = new CityManagmentPresenter(this, fragment);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("hasDeleteId",fragment.getHasDeleteId());
        setResult(ActivityResultCodeUtil.DELETE,intent);
        super.onBackPressed();


    }

    @Override
    protected void loadData() {

    }

}
