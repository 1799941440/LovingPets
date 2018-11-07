package com.example.wz.lovingpets.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.wz.lovingpets.activity.MyApp;
import com.example.wz.lovingpets.common.BindEventBus;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().unregister(this);
        }
//        RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());
//        refWatcher.watch(this);//对所有fragment进行检测是否有泄漏
    }
    public void intent2Activity(Class<? extends Activity> tarActivity){
        Intent intent = new Intent(getActivity(), tarActivity);
        startActivity(intent);
    }

    /**
     * 带参数的跳转
     * @param tarActivity 目标activity
     * @param b 数据载体，Bundle类型
     */
    public void intentWithData(Class<? extends Activity> tarActivity, Bundle b) {
        Intent intent = new Intent(getActivity(), tarActivity);
        if (b != null && b.size() != 0) {
            intent.putExtra("bundle", b);
        }
        startActivity(intent);
    }

    public void showToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }
}
