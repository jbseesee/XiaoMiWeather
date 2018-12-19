package com.example.jb.test4;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by 666 on 2018/5/13.
 */

public class MyApplication extends Application {
    public static Context getContext() {
        return context;
    }

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        context = getApplicationContext();
    }
}
