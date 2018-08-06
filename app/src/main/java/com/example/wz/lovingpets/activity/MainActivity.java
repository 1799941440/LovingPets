package com.example.wz.lovingpets.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.ActivityManager;
import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.fragment.CircleFragment;
import com.example.wz.lovingpets.fragment.ClassifyFragment;
import com.example.wz.lovingpets.ui.main_home.HomeFragment;
import com.example.wz.lovingpets.fragment.MineFragment;
import com.example.wz.lovingpets.fragment.ShoppingTrolleyFragment;
import com.example.wz.lovingpets.utils.StatusBarUtil;
import com.example.wz.lovingpets.widget.BottomBarItem;
import com.example.wz.lovingpets.widget.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * APP的主界面，包含五个fragment
 * 由于含有fragment，继承baseactivity不能获取碎片管理器，
 */
public class MainActivity extends BaseFragmentActivity {

    private long exitTime;//记录上一次点击返回键的时间，若再次点击时间间隔小于临界值时就退出
    private List<Fragment> mFragmentList = new ArrayList<>();//承载碎片的list
    private FrameLayout mFlContent;//碎片填充的地方
    private BottomBarLayout mBottomBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = MyApp.getInstance().getCurrentTheme();//从sharedprefre获取存储的主题
        setTheme(Constant.allThemes[theme]);//设置主题为该主题，要放在setcontentview前
        setContentView(R.layout.activity_main);
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        ActivityManager.getInstance().add(this);//由于没继承baseactivity，手动添加进入栈管理器
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

    /**
     * 使用网上一个开源的bottombarlayout，设置点击事件，跳转到不同的fragment
     */
    private void initListener() {
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                Log.i("MainActivity", "position: " + currentPosition);
//                执行跳转
                changeFragment(currentPosition);

                if (currentPosition == 0) {
                    //如果是第一个，即首页，还是点击首页的话则跳转到切换站点
                    if (previousPosition == currentPosition) {
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

    /**
     * 切换fragment
     * @param currentPosition
     */
    private void changeFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, mFragmentList.get(currentPosition));
        transaction.commit();
    }

    /**
     * 对返回键进行拦截，实现双击退出应用
     * @param keyCode
     * @param event
     * @return
     */
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
