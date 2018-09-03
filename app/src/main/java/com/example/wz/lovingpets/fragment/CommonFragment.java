package com.example.wz.lovingpets.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.entity.User;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@BindEventBus
public class CommonFragment extends BaseFragment {
    public static final String FAMILY = "family";
    public static final String GOODS_CLASSIFY = "goods_classify";
    private String mParam1;
    private int mParam2;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(FAMILY);
            mParam2 = getArguments().getInt(GOODS_CLASSIFY);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayouts(), container, false);
        return view;
    }
    public static CommonFragment newInstance(String family,int goods_classify) {
        CommonFragment fragment = new CommonFragment();
        Bundle args = new Bundle();
        args.putString(FAMILY, family);
        args.putInt(GOODS_CLASSIFY, goods_classify);
        fragment.setArguments(args);
        return fragment;
    }

    private int getLayouts(){
        return Constant.mainLayouts[mParam2];
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccess(Event<User> event){

    }
}
