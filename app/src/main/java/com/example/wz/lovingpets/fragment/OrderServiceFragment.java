package com.example.wz.lovingpets.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.MyApp;
import com.example.wz.lovingpets.activity.ServiceListActivity;
import com.example.wz.lovingpets.adapter.ShopCartAdapter;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.ShoppingCartDetail;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.DecimalUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class OrderServiceFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout ll_0,ll_1,ll_2,ll_3,ll_4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_shopcart,container,false);
        findViews(view);
        initData();
        return view;
    }

    private void findViews(View view) {
        ll_0 = view.findViewById(R.id.service_ll_0);
        ll_1 = view.findViewById(R.id.service_ll_1);
        ll_2 = view.findViewById(R.id.service_ll_2);
        ll_3 = view.findViewById(R.id.service_ll_3);
        ll_4 = view.findViewById(R.id.service_ll_4);
    }

    private void initData() {
        ll_0.setOnClickListener(this);
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        ll_4.setOnClickListener(this);
    }


    public static OrderServiceFragment newInstance() {
        OrderServiceFragment fragment = new OrderServiceFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(),ServiceListActivity.class);
        switch (v.getId()){
            case R.id.service_ll_0:
                intent.putExtra("type",0);
                startActivity(intent);
                break;
            case R.id.service_ll_1:
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.service_ll_2:
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case R.id.service_ll_3:
                intent.putExtra("type",3);
                startActivity(intent);
                break;
            case R.id.service_ll_4:
                intent.putExtra("type",4);
                startActivity(intent);
                break;
        }
    }
}
