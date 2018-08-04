package com.example.wz.lovingpets.activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.wz.lovingpets.R;

public class MyApp extends Application {

    public static int currentTheme; //
    public static MyApp instance; //
    public static Context context; //
    public SharedPreferences sp; //
    private final int[] allThemes = {R.style.AppTheme_dog, R.style.AppTheme_cat, R.style.AppTheme_bird, R.style.AppTheme_fish};


    public static MyApp getInstance() {
        return instance;
    }

    public int getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(int currentThemeId) {
        MyApp.currentTheme = allThemes[currentThemeId];
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currentTheme",currentTheme);
        editor.commit();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        instance = this; //初始化实例
        sp = context.getSharedPreferences("theme", MODE_PRIVATE);//初始化sp
        currentTheme = sp.getInt("currentTheme",allThemes[0]);//获取sp存储的主题
    }
}
