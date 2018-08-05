package com.example.wz.lovingpets.activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.wz.lovingpets.R;

/**
 * 重写的application，用于获取一些APP全局的变量
 */
public class MyApp extends Application {

    public static int currentTheme; //当前主题
    public static MyApp instance; //应用实例
    public static Context context; //
    public SharedPreferences sp; //
    private final int[] allThemes = {R.style.AppTheme_dog, R.style.AppTheme_cat, R.style.AppTheme_bird, R.style.AppTheme_fish};

    /**
     * 获取实例
     * @return 返回实例
     */
    public static MyApp getInstance() {
        return instance;
    }

    /**
     *获取存储在sp里的当前主题
     * @return
     */
    public int getCurrentTheme() {
        return currentTheme;
    }

    /**
     * 切换主题
     * @param currentThemeId 0代表狗主题、1代表猫、2代表鸟、3代表鱼。
     */
    public void setCurrentTheme(int currentThemeId) {
        currentTheme = allThemes[currentThemeId];//
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
