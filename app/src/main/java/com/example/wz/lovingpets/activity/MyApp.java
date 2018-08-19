package com.example.wz.lovingpets.activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.common.LogCatStrategy;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * 重写的application，用于获取一些APP全局的变量以及初始化一些框架
 */
public class MyApp extends Application {

    public static int currentTheme; //当前主题
    public static MyApp instance; //应用实例
    public static Context context; //
    public SharedPreferences sp; //
    private RefWatcher refWatcher;

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
     * @param currentTheme 0代表狗主题、1代表猫、2代表鸟、3代表鱼。
     */
    public void setCurrentTheme(int currentTheme) {
        this.currentTheme = currentTheme;//
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
        currentTheme = sp.getInt("currentTheme",0);//获取sp存储的主题
        initLogger();//初始化Logger日志记录器
        initLeakCanary();//初始化内存泄漏检测
    }

    /**
     * 用于监测fragment和其他对象的实例
     * @param context
     * @return
     */
    public static RefWatcher getRefWatcher(Context context) {
        MyApp application = (MyApp) context.getApplicationContext();
        return application.refWatcher;
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    //初始化日志记录器
    private void initLogger() {
        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .logStrategy(new LogCatStrategy())
                .methodCount(2)
                .tag("Logger")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy));
        Logger.i("onCreate");
    }
}
