package com.example.jb.test4.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.PointF;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.example.jb.test4.MyApplication;
import com.example.jb.test4.db.SharedPreferencesUtil;

import java.text.DecimalFormat;

/**
 * Created by 666 on 2018/4/29.
 */

public class Util {

    public static long getID() {
        return System.currentTimeMillis();

    }


    public static float dpToPixelF(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }

    public static float spToPx(int sp) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (scaledDensity * sp);
    }

    public static float getpercent(long sunRise, long sunSet) {
        long currenttime = System.currentTimeMillis();
        float preTime = currenttime - sunRise;
        float percent = 0;
        if (preTime <= 0) {
            percent = 0;
        } else {
            percent = (preTime / (sunSet - sunRise));
            percent = ((int) (percent * 100)) / 100.0f;
            if (percent >= 1) {
                percent = 1;
            }
            Log.d("tag", "percent: " + percent);
        }
        return percent;
    }

    public static PointF getPointFromQuadBezier(float t, PointF p0, PointF p1, PointF p2) {
        PointF point = new PointF();
        float temp = 1 - t;
        point.x = temp * temp * p0.x + 2 * t * temp * p1.x + t * t * p2.x;
        point.y = temp * temp * p0.y + 2 * t * temp * p1.y + t * t * p2.y;
        return point;
    }

    public static String handleLocation(double latitude, double longitude) {
        String location = new DecimalFormat("0.0000").format(longitude) + ","
                + new DecimalFormat("0.0000").format(latitude);

        return location;
    }

    public static boolean isFirstStrat() {
        return SharedPreferencesUtil.getmInstance().getBoolean("isFirst");
    }

    public static boolean isNetworkConnected() {
        Context context = MyApplication.getContext();
        boolean ok = false;
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                ok = mNetworkInfo.isAvailable();
            }
        }
        if (!ok) {
            Toast.makeText(context, "无网络服务", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    public static boolean hasLocatePermission(Activity activity) {
        if (activity != null) {
            LocationManager lm = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
            boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (ok) {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {// 没有权限，申请权限。
                    Toast.makeText(activity, "请打开定位权限", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    return true;
                }
            } else {
                Toast.makeText(activity, "定位服务已经关闭，请打开定位服务", Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        return false;

    }


    //把“市”和“省”字去掉
    public static String handleString(String name){
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
