package com.example.wz.lovingpets.base;

import android.support.v4.app.Fragment;

import com.example.wz.lovingpets.activity.MyApp;
import com.squareup.leakcanary.RefWatcher;

public class BaseFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());
        refWatcher.watch(this);//对所有fragment进行检测是否有泄漏
    }
}
