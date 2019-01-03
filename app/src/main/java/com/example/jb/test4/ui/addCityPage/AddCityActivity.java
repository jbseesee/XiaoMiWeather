package com.example.jb.test4.ui.addCityPage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.example.jb.test4.R;
import com.example.jb.test4.base.BaseActivity;
import com.example.jb.test4.db.City;
import com.example.jb.test4.ui.homePage.HomePageActivity;
import com.example.jb.test4.util.ActivityResultCodeUtil;

import org.litepal.LitePal;

public class AddCityActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_city;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        AddCityFragment addCityFragment = (AddCityFragment)getSupportFragmentManager().findFragmentById(R.id.add_city_layout);
        if (addCityFragment==null) {
            addCityFragment = new AddCityFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.add_city_layout, addCityFragment, null);
            transaction.commit();
        }
        AddCityPresenter presenter = new AddCityPresenter(this, addCityFragment);

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (LitePal.count(City.class)==0){
            setResult(ActivityResultCodeUtil.FINISH,new Intent(AddCityActivity.this, HomePageActivity.class));
        }


    }

    public static void startAddActivity(Context context, long[] hasDeleteId){
        Intent intent = new Intent(context,AddCityActivity.class);
        if (hasDeleteId.length!=0){
            intent.putExtra("hasDeleteId",hasDeleteId);
        }
        context.startActivity(intent);
    }

}
