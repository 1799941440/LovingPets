package com.example.wz.lovingpets.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.MyApp;
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

@BindEventBus
public class ShoppingTrolleyFragment extends BaseFragment {
    private RecyclerView rv;
    private TextView tv_total;
    private CheckBox cb_all;
    private Button bt_pay;
    private String mParam1,mParam2;
    private ShopCartAdapter adapter;
    private ShopCartAdapter.CkeckBoxListener cbl;
    public static final String TEXT_TITLE = "content";
    private HttpRequest.ApiService api = HttpRequest.getApiservice();
    private List<ShoppingCartDetail> list_shopcart = new ArrayList<>();

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
        View view = inflater.inflate(R.layout.layout_fragment_shopcart,container,false);
        findViews(view);
        initData();
        return view;
    }

    private void findViews(View view) {
        rv = view.findViewById(R.id.shopcart_rv);
        tv_total = view.findViewById(R.id.shopcart_tv_total);
        cb_all = view.findViewById(R.id.shopcart_cb_all);
        bt_pay = view.findViewById(R.id.shopcart_bt_pay);
    }

    private void initData() {
        getSC();
        cbl = new ShopCartAdapter.CkeckBoxListener() {
            @Override
            public void onCkeckboxSwitch(String amount) {
                tv_total.setText("￥" + amount);
            }
        };
        cb_all.setChecked(false);
        cb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_all.isChecked()){
                    selectAll(list_shopcart,cb_all.isChecked());
                }else{
                    tv_total.setText("0.0");
                    adapter.setAmount("0.0");
                    unSelectAll(list_shopcart,cb_all.isChecked());
                }
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new ShopCartAdapter(list_shopcart,getContext(),cb_all);
        adapter.setCbl(cbl);
        rv.setAdapter(adapter);
        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payForCart();
            }
        });
    }

    private void payForCart() {

    }

    private void unSelectAll(List<ShoppingCartDetail> list,boolean b) {
        for(ShoppingCartDetail s :list){
            s.setSelected(b);
        }
    }

    private void selectAll(List<ShoppingCartDetail> list,boolean b) {
        String total = "0.0";
        for(ShoppingCartDetail s :list){
            total = DecimalUtil.add(total,String.valueOf(s.getTotal()));
            s.setSelected(b);
        }
        tv_total.setText(total);
        adapter.setAmount(total);
    }

    private void getSC(){
        Observable<ListResponse<ShoppingCartDetail>> observable = api.getSC(MyApp.getInstance().getUser().getShoppingcartId());
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<ShoppingCartDetail>>() {
            @Override
            public void onSuccess(ListResponse<ShoppingCartDetail> listResponse) {
                loadDataSuccess(listResponse);
            }
        });
    }

    private void loadDataSuccess(ListResponse<ShoppingCartDetail> listResponse) {
        if(adapter == null){
            adapter = new ShopCartAdapter(listResponse.getRows(),getContext(),cb_all);
            adapter.setCbl(cbl);
            rv.setAdapter(adapter);
        }else{
            list_shopcart.clear();
            list_shopcart.addAll(listResponse.getRows());
            adapter.notifyDataSetChanged();
        }
    }

    public static ShoppingTrolleyFragment newInstance(String param1) {
        ShoppingTrolleyFragment fragment = new ShoppingTrolleyFragment();
        Bundle args = new Bundle();
        args.putString(TEXT_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccess(Event<User> event){

    }
}
