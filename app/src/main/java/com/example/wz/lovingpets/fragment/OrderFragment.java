package com.example.wz.lovingpets.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.OrderAdapter;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.entity.OrderInfo;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.List;

@BindEventBus
public class OrderFragment extends BaseFragment {
    public static final String TEXT_TITLE = "orderState";
    private View view;
    private RecyclerView rv;
    private OrderAdapter adapter;
    private List<OrderInfo> list = new ArrayList<>();
    private int orderState;//订单状态(0-待付款、1-订单失效、2-待收货、3-交易成功、4-全部)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderState = getArguments().getInt(TEXT_TITLE);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_layout_order, container, false);
        view = rootView;
        findViews(view);
        initDatas();
        return rootView;
    }

    private void findViews(View view) {
        rv = view.findViewById(R.id.order_rv);
    }

    private void initDatas() {
        adapter = new OrderAdapter(getContext(),list);
        rv.setAdapter(adapter);
    }

    public static OrderFragment newInstance(int orderState) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt(TEXT_TITLE, orderState);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void successLoadOrder(Event<List<OrderInfo>> event){
        if(event.getCode() == EventCodes.LOADED_ORDER){
            list.clear();
            if (orderState == 4) {
                list.addAll(event.getData());
                adapter.notifyDataSetChanged();
                return;
            }
            for (OrderInfo o : event.getData()){
                if(o.getOrderState() == orderState){
                    list.add(o);
                }
            }
            if(adapter == null){
                adapter = new OrderAdapter(getContext(),list);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
