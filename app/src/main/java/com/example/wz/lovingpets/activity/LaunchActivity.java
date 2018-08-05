package com.example.wz.lovingpets.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.ui.login.LoginActivity;

/**
 * 应用启动的第一个activity，由于方法较少，直接继承activity而非baseactivity
 * 用于检测登录状态、版本更新
 */
public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        gotoLogin();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    //取消界面跳转时的动画，使启动页的logo图片与注册、登录主页的logo图片完美衔接
                    overridePendingTransition(R.anim.trans_in, R.anim.trans_in);
                    break;
                default:
                    break;
            }

        }
    };

    /**
     * 跳转进入登录页面
     */
    private void gotoLogin() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 屏蔽物理返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            //If token is null, all callbacks and messages will be removed.
            //清空handler的消息，防止内存泄漏
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
