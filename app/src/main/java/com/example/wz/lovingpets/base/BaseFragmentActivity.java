package com.example.wz.lovingpets.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.wz.lovingpets.common.ActivityManager;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 功能与BaseActivity基本相似
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
    public boolean isActive = false;
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().register(this);
        }
        ActivityManager.getInstance().add(this);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

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

    protected abstract void findViews();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().unregister(this);
        }
        ActivityManager.getInstance().remove(this);
    }
}
