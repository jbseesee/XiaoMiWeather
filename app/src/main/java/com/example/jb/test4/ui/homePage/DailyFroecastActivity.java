package com.example.jb.test4.ui.homePage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jb.test4.bean.homeBean.DailyFroecastData;
import com.example.jb.test4.widgets.DailyView;
import com.example.jb.test4.R;
import com.example.jb.test4.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

public class DailyFroecastActivity extends BaseActivity {

    DailyView dailyView;
    ImageView back_iv;


    public static void startDailyFroecastActivity(Context context, DailyFroecastData dailyFroecastData){
        Map<String,DailyFroecastData> map = new HashMap<>();
        map.put("dailyData",dailyFroecastData);
        DailyDataPar parcel = new DailyDataPar();
        parcel.setMap(map);
        Intent intent =new Intent(context,DailyFroecastActivity.class);
        intent.putExtra("getData",parcel);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.dailyforecast_fragment;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        dailyView = findViewById(R.id.dailyView);
        back_iv = findViewById(R.id.dailyForecastFragment_back);
        dailyView.invalidate();

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    @Override
    protected void loadData() {
        Intent intent = getIntent();
        DailyDataPar parcel = intent.getParcelableExtra("getData");
        DailyFroecastData dailyFroecastData = parcel.getMap().get("dailyData");
        dailyView.setData(dailyFroecastData);
    }
}
