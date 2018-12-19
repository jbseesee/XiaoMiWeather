package com.example.jb.test4.Model;

import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.jb.test4.MyApplication;

import java.text.DecimalFormat;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class Location implements AMapLocationListener {
    private int cityId;

    private AMapLocationClient mLocationClient ;
    private AMapLocationClientOption mLocationOption ;
    private static Location instance;
    private AMapLocationListener aMapLocationListener;
    private Observable observable;

    public static  Location getInstance(){
        if (instance == null){
            instance = new Location();
        }
        return instance;
    }

    private Location() {
        init();
    }


    private void init(){
        mLocationClient = new AMapLocationClient(MyApplication.getContext());
        mLocationOption = new AMapLocationClientOption();

        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //获取一次定位结果：
            //该方法默认为false。
            mLocationOption.setOnceLocation(true);
            //获取最近3s内精度最高的一次定位结果：
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation
            mLocationOption.setOnceLocationLatest(true);
            mLocationOption.setNeedAddress(true);
            mLocationOption.setHttpTimeOut(8000);
            mLocationOption.setLocationCacheEnable(true);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
        }
    }

    public Observable<AMapLocation> start(){
        if (observable == null) {
            observable = Observable.create(new ObservableOnSubscribe<AMapLocation>() {
                @Override
                public void subscribe(ObservableEmitter<AMapLocation> emitter) throws Exception {

                    aMapLocationListener = new AMapLocationListener() {
                        @Override
                        public void onLocationChanged(AMapLocation aMapLocation) {
                            if (aMapLocation.getErrorCode() == 0) {
                                emitter.onNext(aMapLocation);
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable("定位失败"));
                                Toast.makeText(MyApplication.getContext(),"定位失败",Toast.LENGTH_SHORT).show();
                                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                                Log.d("PROCESS!","定位失败！！！ ErrorCode：" + aMapLocation.getErrorCode());
                            }
                        }
                    };
                    mLocationClient.setLocationListener(aMapLocationListener);
                    mLocationClient.startLocation();
                }
            });
        }
        return observable;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {

            if (amapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude();//获取经度
                String street = amapLocation.getStreet();//获取街道；
                String district = amapLocation.getDistrict();
                String cityName = amapLocation.getCity();
                String loaction = handleLocation(latitude,longitude);
                String province = amapLocation.getProvince();
                float radius = amapLocation.getAccuracy();//获取精度信息
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.d("PROCESS!","定位失败！！！ ErrorCode：" + amapLocation.getErrorCode());
            }
        }

    }

    private String handleLocation(double latitude, double longitude){
        String location = new DecimalFormat("0.0000").format(longitude) + ","
                + new DecimalFormat("0.0000").format(latitude);

        return location;
    }
    //把“市”和“省”字去掉
    private static String handleString(String name){
        if(name!=null) {
            if (name.contains("市")) {
                String[] line = name.split("市");
                return line[0];
            }else if (name.contains("省")){
                String[] line2 = name.split("省");
                return line2[0];
            }else {
                return name;
            }

        }
        else {
            return name;
        }
    }

}

