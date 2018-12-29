package com.example.wz.lovingpets.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.ShopCartAdapter;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.Address;
import com.example.wz.lovingpets.entity.GoodsDetailInfo;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.ShoppingCartDetail;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.utils.UserUtil;
import com.example.wz.lovingpets.widget.PriceView;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.example.wz.lovingpets.activity.MyApp.getContext;

public class ConfirmOrderActivity extends BaseActivity {

    private User user;
    private PriceView pv;
    private RecyclerView rv;
    private Button bt_commit;
    private ImageView iv_back;
    private int type,addressId;
    private ShopCartAdapter adapter;
    private LinearLayout ll_choose_address;
    private List<ShoppingCartDetail> list = new ArrayList<>();
    private HttpRequest.ApiService api = HttpRequest.getApiservice();
    private TextView tv_receiver, tv_contact, tv_full_address, tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        rv = findViewById(R.id.confirm_order_rv);
        bt_commit = findViewById(R.id.confirm_order_commit);
        iv_back = findViewById(R.id.titlebar_iv_left);
        tv_title = findViewById(R.id.titlebar_tv_title);
        ll_choose_address = findViewById(R.id.confirm_order_choose_address);
        tv_receiver = findViewById(R.id.confirm_order_receiver);
        tv_contact = findViewById(R.id.confirm_order_contact);
        tv_full_address = findViewById(R.id.confirm_order_address);
        pv = findViewById(R.id.confirm_order_pv);
    }

    @Override
    protected void initData() {
        tv_title.setText("确认提交订单");
        user = new UserUtil(getContext()).getUser();
        type = getIntent().getIntExtra("type",0);
        if(type == 1){
            final GoodsDetailInfo data = new Gson().fromJson(getIntent().getStringExtra("data"),GoodsDetailInfo.class);
            final int count = getIntent().getIntExtra("count",0);
            convert(data,count);
            bt_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtils.sendEvent(new Event<String>(EventCodes.BUY_GOODS, data.getId()+" "+count+" "+addressId));
                    finish();
                }
            });
        }else{
            list = new Gson().fromJson(getIntent().getStringExtra("data"),new TypeToken<List<ShoppingCartDetail>>(){}.getType());
            bt_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Observable<ListResponse> observable = api.cartToOrder(getSeletedItem(),user.getId(),addressId,2);
                    ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
                        @Override
                        public void onSuccess(ListResponse listResponse) {
                            if(listResponse.isSuccess()){
                                showLongToast(listResponse.getMsg());
                                finish();
                            }
                        }
                    });
                }
            });
        }
        getDefaultAddress();
        pv.setPrice(getAmount());
        adapter = new ShopCartAdapter(list,getContext(),false);
        rv.setAdapter(adapter);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void convert(GoodsDetailInfo data,int count) {
        ShoppingCartDetail s = new ShoppingCartDetail();
        s.setImage(data.getImage());
        s.setGoodsName(data.getName());
        s.setPrice(data.getPrice());
        s.setCount(count);
        s.setTotal(count*data.getPrice());
        s.setShopName(data.getShopName());
        list.add(s);
    }

    private void getDefaultAddress(){
        Observable<ListResponse<Address>> observable = api.getCommonAddress(user.getId());
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<Address>>() {
            @Override
            public void onSuccess(ListResponse<Address> listResponse) {
                if(listResponse.isSuccess()){
                    setAddressInfo(listResponse.getRows().get(0));
                }else{
                    showLongToast("请先添加一个地址并选为默认收货地址");
                    intent2Activity(ManageAddressActivity.class);
                    finish();
                }
            }
        });
    }

    private void setAddressInfo(Address address){
        addressId = address.getId();
        tv_receiver.setText(address.getReceiver());
        tv_contact.setText(address.getContact());
        tv_full_address.setText("收货地址"+address.getProvince()+address.getCity()
                +address.getFullAddress());
    }

    public String getSeletedItem(){
        StringBuilder sb = new StringBuilder();
        for (ShoppingCartDetail s :list){
            if(s.isSelected()){
                sb.append(s.getId());
                sb.append("-");
            }
        }
        String res = sb.toString();
        if(res.length() == 0){
            return "";
        }else{
            return res.substring(0,res.length()-1);
        }
    }

    public float getAmount(){
        float result = 0f;
        for (ShoppingCartDetail s :list){
            result += s.getTotal();
        }
        return  result;
    }
}
