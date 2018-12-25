package com.example.jb.test4.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.jb.test4.ui.homePage.ScrollViewListener;
import com.example.jb.test4.util.Util;
import com.example.jb.test4.ui.homePage.RefreshListener;

public class MyScrollView extends ScrollView {
    int lastX = 0;
    int lastY = 0;
//这个view用于获得ChatView的在屏幕上的位置
    View chatView;
    View backgroudView;

    RelativeLayout.LayoutParams params;
    int[] viewLocation = new int[2];


    int dispatchLastX;
    int dispatchLastY;

    private ScrollViewListener scrollViewListener;


    int disT;//竖直滑动累加值
    int scrollY;//视图滑动的Y值
    float maxDis = Util.dpToPixelF(60);//刷新视图的最大位移值
    RefreshListener refreshListener;//下拉刷新回调接口；

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //获得ChatView的在屏幕上的位置
       chatView.getLocationOnScreen(viewLocation);
       scrollY = t;//视图滑动的Y值
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int)ev.getX();
        int y = (int) ev.getY();
        int rawY = (int)ev.getRawY();
        int top = viewLocation[1];
        int dropDownHeight = backgroudView.getLayoutParams().height;

        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if (ev.getAction() == MotionEvent.ACTION_MOVE){
            int detalX = x - dispatchLastX;
            int detalY = y - dispatchLastY;
            //垂直滑动请求父View不要拦截事件
            if (Math.abs(detalY) > Math.abs(detalX)) {
                getParent().requestDisallowInterceptTouchEvent(true);

                disT =scrollY>0?0:(disT+detalY);
                //触摸点在ChatView位置父View不要拦截事件
            }else if (rawY > top && rawY < top + chatView.getHeight()){
                getParent().requestDisallowInterceptTouchEvent(true);
            }else {
                getParent().requestDisallowInterceptTouchEvent(false);
            }


            if (disT > maxDis) {
                disT = (int) maxDis;//累加值的最大值
            }
            //下拉背景iv拉长，刷新控件跟随下移

            if (detalY > 0 && scrollY == 0) {
                params.height = (int) (Util.dpToPixelF(350) + disT);
                backgroudView.setLayoutParams(params);
            }

        }

        if (ev.getAction() == MotionEvent.ACTION_UP){
            //下拉刷新
            if (scrollY == 0 && disT >= maxDis) {
                refreshListener.refreshing();
            }
            //下拉不到位时复位
            else if (dropDownHeight > Util.dpToPixelF(350) && disT < maxDis) {
                params.height = (int) Util.dpToPixelF(350);
                backgroudView.setLayoutParams(params);
                scrollBy(0, -scrollY);
            }
            disT = 0;
        }


        dispatchLastX = x;
        dispatchLastY = y;
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int)ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                super.onInterceptTouchEvent(ev);//必须在DOWN事件调用父方法，否则ScrollView无法滑动
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int detalX = x - lastX;
                int detalY = y - lastY;
                //如果滑动的竖直距离大于水平距离，拦截事件，不传给子
                if (Math.abs(detalY) > Math.abs(detalX)) {
                    intercept = true;
                    
                    break;
                } else
                    intercept = false;

                    break;
            case MotionEvent.ACTION_UP:
                intercept = false;

                break;
        }

        lastX = x;
        lastY = y;
        return intercept;

    }

    public void setChatView(View view){
        this.chatView = view;
    }
    public void setBackgroundView(View view){
        this.backgroudView = view;
       params = (RelativeLayout.LayoutParams)backgroudView.getLayoutParams();
    }

    public void setRefreshListener(RefreshListener refreshListener){
        this.refreshListener = refreshListener;
    }


}
