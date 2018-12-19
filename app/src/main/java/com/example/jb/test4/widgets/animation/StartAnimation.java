package com.example.jb.test4.widgets.animation;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.jb.test4.R;
import com.example.jb.test4.util.Util;
import com.example.jb.test4.widgets.BirdView;
import com.example.jb.test4.widgets.TreeView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by 666 on 2018/4/26.
 */

public class StartAnimation {

    private Handler mhandler = new Handler();
    private View view;
    private ImageView cloudy1IV;
    private ImageView cloudy2IV;
    private ImageView cloudy3IV;
    private ImageView cloudy4IV;
    private ImageView windmillHead1IV;
    private ImageView windmillHead2IV;
    private ImageView windmillHead3IV;
    private ImageView windmillHead4IV;
    private AnimatorSet animatorSet;
    private CompositeDisposable compositeDisposable;

    private String weather;

    public StartAnimation(View view){
        this.view = view;
        compositeDisposable = new CompositeDisposable();

    }



    public void windmillHead(final View view , final long time) {

        Disposable disposable =  Observable.intervalRange(0,1024,0,time,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        view.animate()
                                .rotationBy(360)
                                .setInterpolator(new LinearInterpolator())
                                .setDuration(time);
                    }
                });

        compositeDisposable.add(disposable);

    }

    public void cloudy(final View view, final float startX, final long time) {
        Disposable disposable =  Observable.intervalRange(0,1024,0,time,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        view.setX(-startX);
                        view.animate()
                                .translationX(startX)
                                .setInterpolator(new LinearInterpolator())
                                .setDuration(time)
                                .start();
                    }
                });

        compositeDisposable.add(disposable);

    }


    public void cloudyAnimation(){
        cloudy1IV = view.findViewById(R.id.bg_cloudy_first_cloud);
        cloudy2IV = view.findViewById(R.id.bg_cloudy_second_cloud);
        cloudy3IV = view.findViewById(R.id.bg_cloudy_third_cloud);
        cloudy4IV = view.findViewById(R.id.bg_cloudy_fourth_cloud);
        windmillHead1IV = view.findViewById(R.id.bg_cloudy_windmill_first_head_violet);
        windmillHead2IV = view.findViewById(R.id.bg_cloudy_windmill_second_head_violet);
        windmillHead3IV = view.findViewById(R.id.bg_cloudy_windmill_third_head_violet);
        windmillHead4IV = view.findViewById(R.id.bg_cloudy_windmill_fourth_head_violet);
        windmillHead(windmillHead1IV,6000);
        windmillHead(windmillHead2IV,8000);
        windmillHead(windmillHead3IV,8000);
        windmillHead(windmillHead4IV,12000);
        cloudy(cloudy1IV, Util.dpToPixelF(380),20000);
        cloudy(cloudy2IV,Util.dpToPixelF(380),30000);
        cloudy(cloudy3IV,Util.dpToPixelF(380),25000);
        cloudy(cloudy4IV,Util.dpToPixelF(380),35000);


    }

    public void start(String weather){
        this.weather = weather;

        if (weather.equals("晴")){
            if (animatorSet!= null){
                animatorSet.resume();
            }else {
                sunnyAnimation();
            }
        }
        else if(weather.equals("雨")){

        }else{
           cloudyAnimation();
        }
    }

    public void reStart(){
        start(weather);
    }

    public void cancle(){
        if (animatorSet!=null) {
            animatorSet.pause();
        }
        compositeDisposable.clear();
    }



    public void sunnyAnimation(){
        animatorSet = new AnimatorSet();
        animatorSet.cancel();
        BirdView birdView = view.findViewById(R.id.birdview);
        TreeView treeView = view.findViewById(R.id.treeview);
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator birdAnimator1 = ObjectAnimator.ofInt(birdView,"n",0,15);
                birdAnimator1.setDuration(800);
                birdAnimator1.setInterpolator(new LinearInterpolator());
                birdAnimator1.setRepeatCount(ValueAnimator.INFINITE);

                ObjectAnimator birdAnimator2 = ObjectAnimator.ofFloat(birdView,"t",0,1);
                birdAnimator2.setDuration(8000);
                birdAnimator2.setInterpolator(new LinearInterpolator());
                birdAnimator2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        birdAnimator1.cancel();
                    }
                });


                //树叶消失动画关键帧
                Keyframe kLA1 = Keyframe.ofInt(0,255);
                Keyframe kLA2 = Keyframe.ofInt(0.5625f,255);
                Keyframe kLA3 = Keyframe.ofInt(0.75f,0);
                Keyframe kLA4 = Keyframe.ofInt(0.1f,0);

                //树干摇动动画关键帧
                Keyframe kTR1 = Keyframe.ofInt(0f,0);
                Keyframe kTR2 = Keyframe.ofInt(0.25f,-15);
                Keyframe kTR3 = Keyframe.ofInt(0.75f,-15);
                Keyframe kTR4 = Keyframe.ofInt(1f,0);

                //树叶飘落动画关键帧
                Keyframe kLM1 = Keyframe.ofFloat(0f,0);
                Keyframe kLM2 = Keyframe.ofFloat(0.25f,0);
                Keyframe kLM3 = Keyframe.ofFloat(0.75f,1);

                Keyframe kLR1 = Keyframe.ofInt(0f,0);
                Keyframe kLR2 = Keyframe.ofInt(0.25f,0);
                Keyframe kLR3 = Keyframe.ofInt(0.75f,270);




                PropertyValuesHolder p1 = PropertyValuesHolder.ofKeyframe("t",kLM1,kLM2,kLM3);
                PropertyValuesHolder p2 = PropertyValuesHolder.ofKeyframe("angle",kLR1,kLR2,kLR3);
                PropertyValuesHolder p3 = PropertyValuesHolder.ofKeyframe("alphap",kLA1,kLA2,kLA3,kLA4);
                PropertyValuesHolder p4 = PropertyValuesHolder.ofKeyframe("treeAngle",kTR1,kTR2,kTR3,kTR4);

                ObjectAnimator treeAnimator = ObjectAnimator.ofPropertyValuesHolder(treeView,p1,p2,p3,p4);
                treeAnimator.setDuration(4000);
                treeAnimator.setInterpolator(new LinearInterpolator());
                treeAnimator.setStartDelay(8000);


                animatorSet.playTogether(birdAnimator1,birdAnimator2,treeAnimator);
                animatorSet.start();

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        sunnyAnimation();
                    }
                });
            }
        },2000);
    }

}
