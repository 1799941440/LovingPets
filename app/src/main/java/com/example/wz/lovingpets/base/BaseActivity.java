package com.example.wz.lovingpets.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {
    public boolean isActive = false;
    protected final String TAG = this.getClass().getSimpleName();

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void findViews();

    protected abstract void initData();
}
