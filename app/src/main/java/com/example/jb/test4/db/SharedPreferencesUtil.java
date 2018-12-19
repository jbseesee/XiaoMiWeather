package com.example.jb.test4.db;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.jb.test4.MyApplication;

import java.util.Set;

public final class SharedPreferencesUtil {
    private static SharedPreferencesUtil mInstance;
    private static final String FILE_NAME = "weather";

    private static SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
    private static SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    private SharedPreferencesUtil(){

    }

    public static synchronized SharedPreferencesUtil getmInstance() {
        if (mInstance == null){
                mInstance = new SharedPreferencesUtil();
        }
        return mInstance;
    }

    public void put(String key, Object value){
        if (value instanceof String) {
            editor.putString(key, (String) value);
        }
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        }
        if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }
        if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        }

        editor.apply();
    }


    public String getString(String key){

        return sharedPreferences.getString(key,"");
    }
    public int getInteger(String key){

        return sharedPreferences.getInt(key,0);
    }
    public float getFloat(String key){


        return sharedPreferences.getFloat(key,0);
    }
    public long getLong(String key){


        return sharedPreferences.getLong(key,0);
    }
    public boolean getBoolean(String key){


        return sharedPreferences.getBoolean(key,true);
    }
    public Set getSet(String key){


        return sharedPreferences.getStringSet(key,null);
    }

    public void remove(String key){
        editor.remove(key).apply();
    }

}
