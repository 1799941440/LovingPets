package com.example.wz.lovingpets.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.OrderAdapter;
import com.example.wz.lovingpets.adapter.ServerOrderAdapter;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.entity.OrderInfo;
import com.example.wz.lovingpets.entity.ServiceOrderInfo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@BindEventBus
public class ServerOrderFragment extends BaseFragment {
    public static final String TEXT_TITLE = "serverState";
    private View view;
    private RecyclerView rv;
    private ServerOrderAdapter adapter;
    private List<ServiceOrderInfo> list = new ArrayList<>();
    private int orderState;//状态0-待店铺受理 1-等待服务/服务中 2-待支付
    // 3-服务取消 4-服务完成待评价 5-服务完成已评价 6-全部

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
        adapter = new ServerOrderAdapter(getContext(),list);
        rv.setAdapter(adapter);
    }

    public static ServerOrderFragment newInstance(int orderState) {
        ServerOrderFragment fragment = new ServerOrderFragment();
        Bundle args = new Bundle();
        args.putInt(TEXT_TITLE, orderState);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void successLoadOrder(Event<List<ServiceOrderInfo>> event){
        if(event.getCode() == EventCodes.LOADED_SERVER_ORDER){
            list.clear();
            if (orderState == 6) {
                list.addAll(event.getData());
                adapter.notifyDataSetChanged();
                return;
            }
            for (ServiceOrderInfo o : event.getData()){
                if(o.getState() == orderState){
                    list.add(o);
                }
            }
            if(adapter == null){
                adapter = new ServerOrderAdapter(getContext(),list);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
