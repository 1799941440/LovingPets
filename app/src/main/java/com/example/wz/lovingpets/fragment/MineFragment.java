package com.example.wz.lovingpets.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.widget.MyScrollView;

public class MineFragment extends Fragment implements View.OnClickListener,MyScrollView.ScrollListener{
    public static final String TEXT_TITLE = "content";
    private String mParam1;
    private String mParam2;
    private static int height;
    private MyScrollView scrollView;
    private ImageView iv_setting,iv_banner;
    private RelativeLayout rl_title;
    private TextView tv_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TEXT_TITLE);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        findViews(view);
        initDatas();
        initListeners();
        return view;
    }
    public static MineFragment newInstance(String param1) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(TEXT_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public void findViews(View view){
        tv_title = view.findViewById(R.id.mine_tv_title);
        rl_title = view.findViewById(R.id.mine_rl_title);
        scrollView = view.findViewById(R.id.mine_scroll);
        iv_setting = view.findViewById(R.id.mine_iv_setting);
        iv_banner = view.findViewById(R.id.mine_background);
    }
    public void initDatas(){
        iv_setting.setOnClickListener(this);
        iv_banner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_iv_setting:
                Toast.makeText(getContext(),"setting clicked",Toast.LENGTH_LONG).show();
                break;
            case R.id.mine_background:
                Toast.makeText(getContext(),"banner clicked",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onScrollChanged(int x, int y, int oldx, int oldy,int computeVerticalScrollRange) {
        if (y <= 0) {   //设置标题的背景颜色
//            rl_title.setBackgroundColor(Color.argb((int) 0, 144,151,166));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            //Log.i("Tag", "onScrollChanged: Y:"+y);
            float scale = (float) y / height;
            float alpha = (255 * scale);
            tv_title.setTextColor(Color.argb((int) alpha,0,0,0));
            rl_title.setBackgroundColor(Color.argb((int) alpha, 255,255,255));
        } else {    //滑动到banner下面设置普通颜色
            rl_title.setBackgroundColor(Color.argb((int) 255, 255,255,255));
        }
    }

    private void initListeners() {

        ViewTreeObserver vto = iv_banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl_title.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = iv_banner.getHeight();

                scrollView.setScrollViewListener(MineFragment.this);
            }
        });
    }
}
