package com.example.wz.lovingpets.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.common.ActivityManager;
import com.example.wz.lovingpets.utils.StatusBarUtil;

/**
 * 主页更换主站的activity
 */
public class ChangeFavoriteActivity extends Activity implements View.OnClickListener {

    private LinearLayout ll_left, ll_dog, ll_cat, ll_bird, ll_fish;
    private Intent intent;
    private int currentTheme;
    private MyApp instance;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_favorite);
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        overridePendingTransition(0, 0);
        ActivityManager.getInstance().add(this);
        findViews();
        initData();
    }

    protected void findViews() {
        ll_left = findViewById(R.id.change_favorite_left);
        ll_dog = findViewById(R.id.change_favorite_ll_dog);
        ll_cat = findViewById(R.id.change_favorite_ll_cat);
        ll_bird = findViewById(R.id.change_favorite_ll_bird);
        ll_fish = findViewById(R.id.change_favorite_ll_fish);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        ll_left.setOnClickListener(this);
        ll_dog.setOnClickListener(this);
        ll_cat.setOnClickListener(this);
        ll_bird.setOnClickListener(this);
        ll_fish.setOnClickListener(this);
        intent = new Intent(this, MainActivity.class);
        instance = MyApp.getInstance();
        sp = instance.sp;
        currentTheme = MyApp.getInstance().getCurrentTheme();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_favorite_left:
                ChangeFavoriteActivity.this.finish();
                break;
            case R.id.change_favorite_ll_dog:
                if (currentTheme != 0) {//如果当前主题不是0
                    instance.setCurrentTheme(0,-3838182);//将主题切换为0
                    ActivityManager.getInstance().removeAll();//用自定义的activity管理器清空其余activity
                    startActivity(intent);
                    overridePendingTransition(0, 0);//移除动画
                } else {
                    Toast.makeText(this, "当前已经是狗狗主站了哦", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.change_favorite_ll_cat:
                if (currentTheme != 1) {
                    instance.setCurrentTheme(1,-1621626);
                    ActivityManager.getInstance().removeAll();
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    Toast.makeText(this, "当前已经是猫猫主站了哦", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.change_favorite_ll_bird:
                if (currentTheme != 2) {
                    instance.setCurrentTheme(2,-11625930);
                    ActivityManager.getInstance().removeAll();
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    Toast.makeText(this, "当前已经是鸟儿主站了哦", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.change_favorite_ll_fish:
                if (currentTheme != 3) {
                    instance.setCurrentTheme(3,-15491617);
                    ActivityManager.getInstance().removeAll();
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    Toast.makeText(this, "当前已经是鱼儿主站了哦", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);//设置的退场动画无效要在这里设置
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().remove(this);
    }
}
