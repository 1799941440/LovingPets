package com.example.wz.lovingpets.fragment;

import android.content.Entity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.ManageAddressActivity;
import com.example.wz.lovingpets.activity.MyPetsActivity;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.ui.login.LoginActivity;
import com.example.wz.lovingpets.ui.register.RegisterActivity;
import com.example.wz.lovingpets.widget.MyScrollView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@BindEventBus
public class MineFragment extends BaseFragment implements View.OnClickListener,MyScrollView.ScrollListener{
    public static final String TEXT_TITLE = "content";
    private String mParam1;
    private String mParam2;
    private static int height;
    private MyScrollView scrollView;
    private ImageView iv_setting,iv_banner,iv_devide;
    private RelativeLayout rl_title;
    private TextView tv_title,tv_login,tv_register;
    private LinearLayout ll_pets_info,ll_my_address;

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
        tv_login = view.findViewById(R.id.mine_tv_login);
        iv_devide = view.findViewById(R.id.mine_iv_devide);
        tv_register = view.findViewById(R.id.mine_tv_register);
        rl_title = view.findViewById(R.id.mine_rl_title);
        scrollView = view.findViewById(R.id.mine_scroll);
        iv_setting = view.findViewById(R.id.mine_iv_setting);
        iv_banner = view.findViewById(R.id.mine_background);
        ll_pets_info = view.findViewById(R.id.ll_pets_info);
        ll_my_address = view.findViewById(R.id.ll_my_address);
    }
    public void initDatas(){
        iv_setting.setOnClickListener(this);
        iv_banner.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        ll_pets_info.setOnClickListener(this);
        ll_my_address.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccess(Event<User> event){
        if(event.getCode() == 1){
            tv_register.setVisibility(View.GONE);
            iv_devide.setVisibility(View.GONE);
            tv_login.setText(event.getData().getUserName());
        }
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
            case R.id.mine_tv_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.mine_tv_register:
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.ll_pets_info:
                startActivity(new Intent(getActivity(), MyPetsActivity.class));
                break;
            case R.id.ll_my_address:
                startActivity(new Intent(getActivity(), ManageAddressActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onScrollChanged(int x, int y, int oldx, int oldy,int computeVerticalScrollRange) {
        if (y <= 50) {   //设置标题的背景颜色
            tv_title.setTextColor(Color.argb((int) 0,0,0,0));
            rl_title.setBackgroundColor(Color.argb((int) 0, 255,255,255));
        } else if (y > 50 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
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
