package com.example.wz.lovingpets.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.fragment.GoodsCollectFragment;
import com.example.wz.lovingpets.fragment.ThemeCollectFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyCollectionActivity extends BaseFragmentActivity {

    private ViewPager vp;
    private TabLayout tab;
    private ImageView iv_back;
    private TextView tv_title;
    private List<BaseFragment> list_frag = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        vp = findViewById(R.id.collect_vp);
        tab = findViewById(R.id.collect_tab);
        iv_back = findViewById(R.id.titlebar_iv_left);
        tv_title = findViewById(R.id.titlebar_tv_title);
    }

    @Override
    protected void initData() {
        tv_title.setText("我的收藏");
        list_frag = Arrays.asList(GoodsCollectFragment.newInstance(), ThemeCollectFragment.newInstance());
        list_title = Arrays.asList("商品收藏","主题收藏");
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list_frag.get(position);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return list_title.get(position);
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        tab.setupWithViewPager(vp);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
