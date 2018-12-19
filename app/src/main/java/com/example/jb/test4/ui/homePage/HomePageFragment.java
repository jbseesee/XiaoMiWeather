package com.example.jb.test4.ui.homePage;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jb.test4.widgets.ChatView2;
import com.example.jb.test4.bean.homeBean.ChatViewData;
import com.example.jb.test4.bean.homeBean.DailyFroecastData;
import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.util.LoadIconUtil;
import com.example.jb.test4.MyApplication;
import com.example.jb.test4.widgets.MyScrollView;
import com.example.jb.test4.R;
import com.example.jb.test4.bean.homeBean.RealTimeData;
import com.example.jb.test4.widgets.animation.StartAnimation;
import com.example.jb.test4.widgets.SunView;
import com.example.jb.test4.bean.homeBean.SunViewData;
import com.example.jb.test4.bean.homeBean.ThreeDaysData;
import com.example.jb.test4.util.Util;
import com.example.jb.test4.base.BaseFragment;

public class HomePageFragment extends BaseFragment implements HomePageContract.View ,ScrollViewListener,RefreshListener{


    private LinearLayout forecastLayout;
    
    private int c = 0;



    private TextView r_tm_tv;
    private TextView r_lc_tv;
    private TextView r_sc_tv;
    private ImageView r_aqi_iv;
    private TextView r_aqiq_tv;
    private TextView r_rain_tv;
    
    private ImageView refresh_iv;
    private TextView refresh_tx;
    
    private FrameLayout backgroundV;
    private RelativeLayout.LayoutParams params;
    private float maxDis;

    private Button dailyForecast_bt;

    private ChatView2 chatView;

    private RelativeLayout tabLayout;

    private SunView sunView;
    private TextView s_windDirect;
    private TextView s_windLevel;
    private TextView s_humidity;
    private TextView s_temperature;
    private TextView s_pressure;

    private ImageView tab_Icon;
    private TextView tab_District;
    private TextView tab_Street;
    private TextView tab_temperature;
    

    private float percent;
    private float scrollDisT;


    private HomePageContract.Presenter presenter;
    private StartAnimation startAnimation;

    private long id;

    private Context context;


    private HomeViewData homeViewData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_layout;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        id = getArguments().getLong("id");
        context = getContext();
        refresh_iv =findView(R.id.refresh_iv);
        refresh_tx =findView(R.id.refresh_tx);

        tabLayout =findView(R.id.tab_layout);

        MyScrollView layout =findView(R.id.scrollView);
        layout.setScrollViewListener(this);
        layout.setRefreshListener(this);
        

        forecastLayout =findView(R.id.forecast_layout);

        r_tm_tv =findView(R.id.realtime_tm_tv);
        r_lc_tv =findView(R.id.location_tv);
        r_sc_tv =findView(R.id.skycon_tv);
        r_aqi_iv =findView(R.id.reatime_aqi_iv);
        r_aqiq_tv =findView(R.id.reatime_aqi_tv);
        r_rain_tv =findView(R.id.reatime_rainfall_tv);

        dailyForecast_bt =findView(R.id.fourDayForecast_bt);

        chatView =findView(R.id.chatView);

        sunView =findView(R.id.sunView);

        s_windDirect =findView(R.id.sunView_windDirect);
        s_windLevel =findView(R.id.sunView_windLevel);
        s_humidity =findView(R.id.sunView_humidity);
        s_temperature =findView(R.id.sunView_temperature);
        s_pressure =findView(R.id.sunView_pressure);

        layout.setChatView(chatView);

        backgroundV =findView(R.id.weather_background);

        layout.setBackgroundView(backgroundV);

        tab_Icon =findView(R.id.tab_icon);
        tab_District =findView(R.id.tab_district);
        tab_Street =findView(R.id.tab_street);
        tab_temperature =findView(R.id.tab_temperture);

        View weatherLayout = LayoutInflater.from(MyApplication.getContext())
                .inflate(R.layout.sunny_layout,backgroundV,false);
        backgroundV.removeAllViews();
        backgroundV.addView(weatherLayout);
        params = (RelativeLayout.LayoutParams)backgroundV.getLayoutParams();

        maxDis = Util.dpToPixelF(60);


        if (saveInstanceState!=null&& homeViewData==null){
            homeViewData =saveInstanceState.getParcelable("getHomeViewData" + id);
            if (homeViewData!=null) {
                showWeather(homeViewData);

            }
        }else if (homeViewData!=null){
            showWeather(homeViewData);
            getArguments().remove("getHomeViewData");

        }
    }

    @Override
    public void showWeather(HomeViewData data) {

        homeViewData = data;
        if (data==null){
            showErrorMessage("加载数据失败！！！");
        }else {
            loadRealTimeData(data.getRealTimeData());
            loadThreeDaysData(data.getThreeDaysData(), data.getDailyFroecastData());
            loadChatViewData(data.getChatViewData());
            loadSunViewData(data.getSunViewData());
            loadTopTabData(data.getRealTimeData());
        }
    }

    @Override
    public void refreshAnimation() {
        startRefreshAnimation();

    }

    @Override
    public void showErrorMessage(String message) {
        endRefreshAnimation();
        Toast.makeText(MyApplication.getContext(),"发送请求失败,请检查网络设置",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        int scrollY = y;
        //现在天气小框根据滑动位置的显示与消隐
        if (scrollY < Util.dpToPixelF(200)) {
            scrollDisT = 0;
            tabLayout.setAlpha(0);
        } else if (scrollY > Util.dpToPixelF(300)) {
            scrollDisT = Util.dpToPixelF(100);
            tabLayout.setAlpha(1);
        } else {
            float dis = y - oldy;
            scrollDisT += dis;
            float tabAlpha = scrollDisT * (1 / Util.dpToPixelF(100));
            tabLayout.setAlpha(tabAlpha);
        }
        
        //滑动到日落部分后开始动画
        if (scrollY >= Util.dpToPixelF(400)) {
            //保证sunview动画每次刷新只执行一次
            if (c == 0) {
                c++;
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(sunView, "percentV", 0, percent);
                objectAnimator.setDuration(2000)
                        .setInterpolator(new LinearInterpolator());
                objectAnimator.start();
            }

        }
    }

    private void loadRealTimeData(RealTimeData data){
        if (data!=null) {
            r_tm_tv.setText(data.getTemperature() + "℃");
            r_lc_tv.setText(data.getDistrict() + data.getStreet());
            r_sc_tv.setText(data.getWeather());
            r_aqi_iv.setImageResource(LoadIconUtil.aqiIcon(data.getAqiQuality()));
            r_aqiq_tv.setText(data.getAqiQuality() + " " + data.getAqi());
            r_rain_tv.setText(data.getDescription());
            r_rain_tv.setSelected(true);
            r_rain_tv.requestFocus();

            initBackground(data.getWeather());
        }
    }

    private void initBackground(String weather){
        int backgroundSource;
        if (weather.equals("晴")){
            backgroundSource = R.layout.sunny_layout;
        }
        else if(weather.equals("雨")){
            backgroundSource = R.layout.rain_layout;
        }else{
            backgroundSource = R.layout.cloudy_layout;
        }
        View weatherLayout = LayoutInflater.from(MyApplication.getContext())
                .inflate(backgroundSource,backgroundV,false);
        backgroundV.removeAllViews();
        backgroundV.addView(weatherLayout);
        startAnimation = new StartAnimation(weatherLayout);
        startAnimation.start(weather);
    }

    private void loadThreeDaysData(ThreeDaysData data,DailyFroecastData dailyFroecastData){


        forecastLayout.removeAllViewsInLayout();
        for (int i = 0; i < 3; i++){
            View Forecastview = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.forecast_threedays_item,forecastLayout,false);

            View view = new View(getContext());
            LinearLayout.LayoutParams margins = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    1);
            view.setBackgroundColor(0xff6a6a6a);

            TextView t_day_tv = Forecastview.findViewById(R.id.days_tv);
            ImageView t_icon_iv = Forecastview.findViewById(R.id.icon_iv);
            TextView t_weather_tv = Forecastview.findViewById(R.id.weather_tv);
            TextView t_aqi_tv = Forecastview.findViewById(R.id.aqi_tv);
            TextView t_tm_tv = Forecastview.findViewById(R.id.tm_tv);
            t_day_tv.setText(data.getDays()[i]);
            t_icon_iv.setImageResource(data.getWeatherIcon()[i]);
            t_weather_tv.setText(data.getWeather()[i]);
            t_aqi_tv.setText(data.getAqiQuality()[i]);
            t_tm_tv.setText(data.getMinT()[i] + "/" + data.getMaxT()[i] + "℃");
            forecastLayout.addView(Forecastview);
            if (i <2){
                forecastLayout.addView(view,margins);
            }
        }

        dailyForecast_bt.setText("4天趋势预报");

        dailyForecast_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyFroecastActivity.startDailyFroecastActivity(context,dailyFroecastData);
            }
        });
    }

    private void loadChatViewData(ChatViewData data){

        chatView.setData(data);
    }

    private void loadSunViewData(SunViewData data){

        sunView.setData(data);

        percent = Util.getpercent(data.getSunRiseL(),data.getSunSetL());
        s_windDirect.setText(data.getWindDirect());
        s_windLevel.setText(data.getWindLevel());
        s_humidity.setText(data.getHumidity());
        s_temperature.setText(data.getTemperature());
        s_pressure.setText(data.getPressure());
    }

    private void loadTopTabData(RealTimeData data){
        tab_Icon.setImageResource(LoadIconUtil.skycon(data.getWeatherValue()));
        tab_District.setText(data.getDistrict());
        tab_Street.setText(data.getStreet());
        tab_temperature.setText(data.getTemperature() + "℃");
    }

    private void startRefreshAnimation() {
        refresh_tx.setText("正在更新");
        refresh_iv.animate()
                .setDuration(10000)
                .translationX(Util.dpToPixelF(5))
                .setInterpolator(new CycleInterpolator(3))
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        endRefreshAnimation();
                    }
                });
        params.height = (int) (Util.dpToPixelF(350) +maxDis);
        backgroundV.setLayoutParams(params);
    }

    @Override
    public void endRefreshAnimation(){
        refresh_iv.animate().cancel();
        refresh_tx.setText("更新完成");
        refresh_iv.setBackgroundResource(R.drawable.refresh_animlist);
        ((AnimationDrawable)refresh_iv.getBackground()).start();
        refresh_iv.setTranslationX(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh_iv.setBackgroundResource(R.drawable.refresh_cloud_shape_0);
                refresh_tx.setText("刚刚更新");
                params.height = (int) Util.dpToPixelF(350);
                backgroundV.setLayoutParams(params);
            }
        },1100);
        c = 0;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("getHomeViewData" + id,homeViewData);
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onStart() {
        super.onStart();
        if (startAnimation!=null){
            startAnimation.reStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter!=null) {
            presenter.subscribe();
            if (homeViewData==null){
                presenter.updateWeather(id);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(startAnimation!=null) {
            startAnimation.cancle();
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();

    }

    @Override
    public void setPresenter(HomePageContract.Presenter presenter) {
        this.presenter =presenter;

    }

    public void setData(HomeViewData data){
        homeViewData = data;
    }

    @Override
    public void refreshing() {
        startRefreshAnimation();
        presenter.updateWeather(id);
    }


    @Override
    public long getCityId(){
        return id;
    }


}
