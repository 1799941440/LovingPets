package com.example.wz.lovingpets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.ActivityManager;
import com.example.wz.lovingpets.fragment.CircleFragment;
import com.example.wz.lovingpets.fragment.ClassifyFragment;
import com.example.wz.lovingpets.fragment.HomeFragment;
import com.example.wz.lovingpets.fragment.MineFragment;
import com.example.wz.lovingpets.fragment.ShoppingTrolleyFragment;
import com.example.wz.lovingpets.utils.StatusBarUtil;
import com.example.wz.lovingpets.widget.BottomBarItem;
import com.example.wz.lovingpets.widget.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {

    private long exitTime;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FrameLayout mFlContent;
    private BottomBarLayout mBottomBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = MyApp.getInstance().getCurrentTheme();
        setTheme(theme);
        setContentView(R.layout.activity_main);
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        Log.d("Tag", "onCreate: theme id"+theme);
        ActivityManager.getInstance().add(this);
        findViews();
        initData();
        initListener();
    }

    @Override
    protected void findViews() {
        mFlContent = findViewById(R.id.fl_content);
        mBottomBarLayout = findViewById(R.id.main_bbl);
    }

    @Override
    protected void initData() {
        mFragmentList.add(HomeFragment.newInstance("主页"));
        mFragmentList.add(ClassifyFragment.newInstance("分类"));
        mFragmentList.add(CircleFragment.newInstance("爱宠圈"));
        mFragmentList.add(ShoppingTrolleyFragment.newInstance("购物车"));
        mFragmentList.add(MineFragment.newInstance("我的"));
        changeFragment(0); //默认显示第一页
    }

    private void initListener() {
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                Log.i("MainActivity", "position: " + currentPosition);

                changeFragment(currentPosition);

                if (currentPosition == 0) {
                    //如果是第一个，即首页
                    if (previousPosition == currentPosition) {
                        //数据刷新完毕
                        intent2Activity(ChangeFavoriteActivity.class);
                    }
                }
            }
        });
//        mBottomBarLayout.setUnread(0, 20);//设置第一个页签的未读数为20
//        mBottomBarLayout.setUnread(1, 1001);//设置第二个页签的未读数
//        mBottomBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
//        mBottomBarLayout.setMsg(3, "NEW");//设置第四个页签显示NEW提示文字
    }

    private void changeFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, mFragmentList.get(currentPosition));
        transaction.commit();
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

//    跳转回传数据
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==0x123&&resultCode==0x123){
//            Bundle db=data.getExtras();
//            int imageId=db.getInt("image");
//            imageView=(ImageView) findViewById(R.id.iview);
//            imageView.setImageResource(imageId);
//        }
//    }
    //设置按钮事件
//    @Override
//    public void onClick(View v){
//        Intent it=new Intent(MainActivity.this,Main2Activity.class);
//        startActivityForResult(it,0x123);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().remove(this);
    }
}
