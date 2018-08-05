package com.example.wz.lovingpets.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.wz.lovingpets.activity.MyApp;
import com.example.wz.lovingpets.common.ActivityManager;
import com.example.wz.lovingpets.utils.StatusBarUtil;

/**
 * activity基类，封装了一些基本常用的方法
 */
public abstract class BaseActivity extends Activity {
    public boolean isActive = false;//用于判断当前activity是否存活
    protected final String TAG = this.getClass().getSimpleName();

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 不带参数的跳转
     * @param tarActivity
     */
    public void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    /**
     * 带参数的条阻焊
     * @param tarActivity 目标activity
     * @param b 数据载体，Bundle类型
     */
    public void intentWithData(Class<? extends Activity> tarActivity, Bundle b) {
        Intent intent = new Intent(this, tarActivity);
        if (b != null && b.size() != 0) {
            intent.putExtra("bundle", b);
        }
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActive = false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int theme = MyApp.getInstance().getCurrentTheme();
        setTheme(theme);
        super.onCreate(savedInstanceState);
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        ActivityManager.getInstance().add(this);//统一使用自定义的activity管理器管理
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().remove(this);
    }

    protected abstract void findViews();

    protected abstract void initData();
}
