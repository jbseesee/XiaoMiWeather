package com.example.jb.test4.ui.homePage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jb.test4.bean.homeBean.HomeViewData;
import com.example.jb.test4.R;
import com.example.jb.test4.util.Util;
import com.example.jb.test4.ui.addCityPage.AddCityActivity;
import com.example.jb.test4.base.BaseActivity;
import com.example.jb.test4.ui.cityManagementPage.CityManagementActivity;
import com.example.jb.test4.db.City;
import com.example.jb.test4.util.ActivityResultCodeUtil;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity implements HomePageContract.ActivityView {

    private ViewPager viewPager;


    private ImageView startAddCityActivity;

    private LinearLayout pageIndication;
    private List<ImageView> imageViews;

    private LinearLayout.LayoutParams margin;

    private MyFragmentAdapter viewPagerAdapter;

    private ActivityPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        viewPager = findViewById(R.id.homeViewpager);
        startAddCityActivity = findViewById(R.id.add_city_iv);
        pageIndication = findViewById(R.id.circleindicator_layout);

        imageViews = new ArrayList<>();

        startAddCityActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startActivity();
            }
        });
        viewPagerPageChange();
    }


    @Override
    protected void loadData() {
        presenter = new ActivityPresenter(this,this);
        presenter.subscribe();

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size()!=0){
            presenter.restoreFragments(fragments);
        }else {
            presenter.loadFragment();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        HomeViewData data =  getIntent().getParcelableExtra("getHomeData");
        long[] hasDeleteId = getIntent().getLongArrayExtra("hasDeleteId");
        if (hasDeleteId!=null){
            presenter.deleteFragment(hasDeleteId);
            notifyDataSetChanged();
        }
        if (data!=null){
            presenter.addFragmentToPager(data);
            getIntent().removeExtra("getHomeData");
        }
    }


    public void viewPagerPageChange(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imageViews.size();i++){
                    City city = presenter.getCity(i);
                    ImageView circle = imageViews.get(i);

                    //导航小圆点选中与非选中时加载的图片
                    if (i == position){
                        if (city.isLocation()){
                            circle.setImageResource(R.drawable.main_circleindicator_located_city_s);
                        }else {
                            circle.setImageResource(R.drawable.main_circleindicator_city_s);
                        }
                    }else {
                        if (city.isLocation()){
                            circle.setImageResource(R.drawable.main_circleindicator_located_city);
                        }else {
                            circle.setImageResource(R.drawable.main_circleindicator_city);
                        }
                    }
                }
                Log.d("test","onPageSelected: " + position );

            }
        });
    }


    @Override
    public void loadCircleindicator(int position){
        int normalWidth = (int) Util.dpToPixelF(5);
        int normalheight = (int) Util.dpToPixelF(5);
        int locationIconW = (int) Util.dpToPixelF(10);
        int locationIconH = (int) Util.dpToPixelF(10);
        int margins = (int) Util.dpToPixelF(2);
        City city = presenter.getCity(position);
        ImageView circle = new ImageView(this);
        if (city.isLocation()) {
            margin = new LinearLayout.LayoutParams(locationIconW, locationIconH);
        } else {
            margin = new LinearLayout.LayoutParams(normalWidth, normalheight);
        }
        margin.setMargins(margins, margins, margins, margins);

        if (position == 0) {//默认选中第一个
            if (city.isLocation()) {
                circle.setImageResource(R.drawable.main_circleindicator_located_city_s);
            } else {
                circle.setImageResource(R.drawable.main_circleindicator_city_s);
            }
        } else {
            if (city.isLocation()) {
                circle.setImageResource(R.drawable.main_circleindicator_located_city);
            } else {
                circle.setImageResource(R.drawable.main_circleindicator_city);
            }
        }

        imageViews.add(circle);
        pageIndication.addView(circle, margin);

    }


    @Override
    public void startAddCityActivity() {
        startActivityForResult(new Intent(HomePageActivity.this,
                AddCityActivity.class),1);
    }

    @Override
    public void startCityManagementActivity() {
        startActivityForResult(new Intent(HomePageActivity.this,
                CityManagementActivity.class),1);
    }

    @Override
    public void initViewPager(List<HomePageFragment> fragments) {
        viewPagerAdapter = new MyFragmentAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void notifyDataSetChanged() {
        if (viewPagerAdapter!=null) {
            viewPagerAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void setCurrentItem(int position) {
        viewPager.setCurrentItem(position);
    }


    public static void startHomePageActivity(Context context, HomeViewData homeViewData,long[] hasDeleteId){
        HomeViewData data = homeViewData;
        Intent intent = new Intent(context,HomePageActivity.class);
        intent.putExtra("getHomeData",data);
        if (hasDeleteId != null){
            intent.putExtra("hasDeleteId",hasDeleteId);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                //没有添加任何城市时退出
                if (resultCode == ActivityResultCodeUtil.FINISH){
                    finish();
                    System.exit(0);
                }

                //清空已添加城市列表，并启动添加城市页面
                if (resultCode == ActivityResultCodeUtil.CLEAR){
                    presenter.clear();
                    notifyDataSetChanged();
                    startActivityForResult(new Intent(HomePageActivity.this,AddCityActivity.class),1);
                }

                //跳转到被点击城市页面，并删除已被删除的城市页面
                if (resultCode == ActivityResultCodeUtil.TURN_TO || resultCode == ActivityResultCodeUtil.DELETE){
                    long[] hasDeleteId = data.getLongArrayExtra("hasDeleteId");
                    if (hasDeleteId.length > 0) {
                        presenter.deleteFragment(hasDeleteId);
                    }
                    notifyDataSetChanged();
                }

                if (resultCode == ActivityResultCodeUtil.TURN_TO) {
                    setCurrentItem(data.getIntExtra("position", 0));
                }
                break;
        }
    }
    @Override
    public void removePointOfPageIndication(int position){
        imageViews.remove(position);
        pageIndication.removeViewAt(position);
    }

    @Override
    public void removeIndication() {
        imageViews.clear();
        pageIndication.removeAllViews();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void setPresenter(HomePageContract.ActivityPresenter presenter) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
