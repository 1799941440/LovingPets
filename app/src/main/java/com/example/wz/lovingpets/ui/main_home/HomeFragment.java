package com.example.wz.lovingpets.ui.main_home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.MyApp;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.fragment.CommonFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@BindEventBus
public class HomeFragment extends BaseFragment {
    public static final String TEXT_TITLE = "content";
    private String mParam1;
    private TabLayout tab;
    private ViewPager vp;
    private int currentTheme;
    private View view;
    private List<Fragment> viewList = new ArrayList<>();
    private List<List<String>> lists = Arrays.asList(
            Arrays.asList("狗狗首页", "狗狗主粮", "医疗保健", "玩具", "外出"),
            Arrays.asList("猫猫首页", "猫猫主粮", "医疗保健", "猫砂猫厕", "玩具"),
            Arrays.asList("鸟儿首页", "鸟儿主粮", "医疗保健", "玩具", "外出"),
            Arrays.asList("鱼儿首页", "鱼儿主粮", "水族药剂", "水缸摆设", "新鱼课堂"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentTheme = MyApp.getInstance().getCurrentTheme();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TEXT_TITLE);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这个if判断可以有效的防止页面切换造成的数据不显示的bug
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        view = rootView;
        findViews(view);
        initDatas();
        return rootView;
    }

    /**
     * 获取带参数的实例
     *
     * @param param1
     * @return
     */
    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(TEXT_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public void findViews(View view) {
        tab = view.findViewById(R.id.home_tab);
        vp = view.findViewById(R.id.home_vp);
    }

    public void initDatas() {
        for (int i = 0; i < lists.get(currentTheme).size(); i++) {
            viewList.add(CommonFragment.newInstance(lists.get(currentTheme).get(i),i));
        }
        for (int i = 0; i < lists.get(currentTheme).size(); i++) {
            tab.addTab(tab.newTab().setText(lists.get(currentTheme).get(i)));//添加tab选项
        }
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return viewList.get(position);
            }

            @Override
            public int getCount() {
                return lists.get(currentTheme).size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return lists.get(currentTheme).get(position);
            }
        };
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccess(Event<User> event){

    }
}
