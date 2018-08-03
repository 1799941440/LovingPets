package com.example.wz.lovingpets.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private long exitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showToast("再按一次退出爱宠APP");
                exitTime = System.currentTimeMillis();
            } else {
                MainActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
