package com.example.wz.lovingpets.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.ActivityManager;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.entity.GoodsDetailInfo;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.fragment.CircleFragment;
import com.example.wz.lovingpets.fragment.ClassifyFragment;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.ui.main_home.HomeFragment;
import com.example.wz.lovingpets.fragment.MineFragment;
import com.example.wz.lovingpets.fragment.ShoppingTrolleyFragment;
import com.example.wz.lovingpets.utils.StatusBarUtil;
import com.example.wz.lovingpets.widget.BottomBarItem;
import com.example.wz.lovingpets.widget.BottomBarLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Logger;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * APP的主界面，包含五个fragment
 * 由于含有fragment，继承baseactivity不能获取碎片管理器，
 */
@BindEventBus
public class MainActivity extends BaseFragmentActivity {

    private long exitTime;//记录上一次点击返回键的时间，若再次点击时间间隔小于临界值时就退出
    private List<Fragment> mFragmentList = new ArrayList<>();//承载碎片的list
    private FrameLayout mFlContent;//碎片填充的目标View
    private BottomBarLayout mBottomBarLayout;
    private final HttpRequest.ApiService api = HttpRequest.getApiservice();
    private BottomBarItem item1;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = MyApp.getInstance().getCurrentTheme();//从sharedprefre获取存储的主题
        setTheme(Constant.allThemes[theme]);//设置主题为该主题，要放在setcontentview前
        setContentView(R.layout.activity_main);
        findViews();
        initData();
        initListener();
    }

    @Override
    protected void findViews() {
        mFlContent = findViewById(R.id.fl_content);
        mBottomBarLayout = findViewById(R.id.main_bbl);
        item1 = findViewById(R.id.nav_item1);
    }

    @Override
    protected void initData() {
        mFragmentList.add(HomeFragment.newInstance("主页"));
        mFragmentList.add(ClassifyFragment.newInstance("分类"));
        mFragmentList.add(CircleFragment.newInstance("爱宠圈"));
        mFragmentList.add(ShoppingTrolleyFragment.newInstance("购物车"));
        mFragmentList.add(MineFragment.newInstance("我的"));
        switchFragment(0); //默认显示第一页
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(Event<GoodsDetailInfo> event){
        if(event.getCode() == 0x123){
            String param = new Gson().toJson(event.getData());
            com.orhanobut.logger.Logger.i("接受数据"+param);
            addToCart(param);
        }
        showToast("成功加入购物车！");
    }
    /**
     * 初始化各种监听
     * 使用网上一个开源的bottombarlayout，设置点击事件，跳转到不同的fragment
     */
    private void initListener() {
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                Log.i("Tag", "currentposition: " + currentPosition+"previousPosition: " + previousPosition);
                if(previousPosition == currentPosition && currentPosition == 0){
                    intent2Activity(ChangeFavoriteActivity.class);
                    return;
                }
                switchFragment(currentPosition);
            }
        });
        mBottomBarLayout.setUnread(3, 20);//设置第4个页签的未读数为20
//        mBottomBarLayout.setUnread(1, 1001);//设置第二个页签的未读数
//        mBottomBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
//        mBottomBarLayout.setMsg(3, "NEW");//设置第四个页签显示NEW提示文字
    }

    private void addToCart(String param){
        Observable<ListResponse> observable = api.addToCart(param);
        observable.subscribeOn(Schedulers.newThread())//它为指定任务启动一个新的线程。
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ListResponse>() {
            @Override
            public void onSubscribe(Disposable d) { }
            @Override
            public void onNext(ListResponse listResponse) {
                showToast(listResponse.getMsg());
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        });
    }
    /**
     * 对返回键进行拦截，实现双击退出应用
     *
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
                ActivityManager.getInstance().removeAll();
                System.exit(0);//使进程死亡，完全退出
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 主页界面的切换
     * @param position 切换目标碎片的position
     */
    private void switchFragment(int position) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        hideFragments();
        switch (position) {
            case 0:
                if (!mFragmentList.get(position).isAdded()) {
                    transaction.add(R.id.fl_content, mFragmentList.get(position));
                }
                item1.setText("切换主站");
                transaction.show(mFragmentList.get(position));
                break;
            case 1:
                if (!mFragmentList.get(position).isAdded()) {
                    transaction.add(R.id.fl_content, mFragmentList.get(position));
                }
                transaction.show(mFragmentList.get(position));
                break;
            case 2:
                if (!mFragmentList.get(position).isAdded()) {
                    transaction.add(R.id.fl_content, mFragmentList.get(position));
                }
                transaction.show(mFragmentList.get(position));
                break;
            case 3:
                if (!mFragmentList.get(position).isAdded()) {
                    transaction.add(R.id.fl_content, mFragmentList.get(position));
                }
                transaction.show(mFragmentList.get(position));
                break;
            case 4:
                if (!mFragmentList.get(position).isAdded()) {
                    transaction.add(R.id.fl_content, mFragmentList.get(position));
                }
                transaction.show(mFragmentList.get(position));
                break;
        }
        transaction.commit();
    }

    /**
     * 切换时隐藏所有fragment
     */
    private void hideFragments() {
        for(Fragment mFragment : mFragmentList){
            if(mFragment != null){
                transaction.hide(mFragment);
            }
        }
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

}
