package com.example.wz.lovingpets.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.GoodsListAdapter;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.GoodsDetailInfo;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.StringUtils;
import com.example.wz.lovingpets.utils.UserUtil;
import com.example.wz.lovingpets.widget.AddToCartDialog;
import com.example.wz.lovingpets.widget.PriceView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@BindEventBus
public class GoodsList extends BaseFragmentActivity {

    private User user;
    private RecyclerView rv;
    private ImageView iv_back;
    private TextView tv_search;
    private EditText et_goodsName;
    public String classify,condition;
    private GoodsListAdapter adapter;
    private List<GoodsDetailInfo> list_goods = new ArrayList<>();
    private final HttpRequest.ApiService api = HttpRequest.getApiservice();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        classify = getIntent().getStringExtra("classify");
        findViews();
        initData();
        getGoods();
    }

    @Override
    protected void findViews() {
        iv_back = findViewById(R.id.goodsList_iv_back);
        rv = findViewById(R.id.goodsList_rv);
        et_goodsName = findViewById(R.id.et_goodsName);
        tv_search = findViewById(R.id.tv_goods_search);
    }

    @Override
    protected void initData() {
        user = new UserUtil(this).getUser();
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condition = et_goodsName.getText().toString().trim();
                getGoods();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new GoodsListAdapter(list_goods,getApplicationContext(),user.getShoppingcartId());
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void getGoods(){
        Observable<ListResponse<GoodsDetailInfo>> observable = api.getGoods(classify,condition,user.getId());
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<GoodsDetailInfo>>() {
            @Override
            public void onSuccess(ListResponse<GoodsDetailInfo> listResponse) {
                if(listResponse.isSuccess()){
                    successLoadGoods(listResponse.getRows());
                }else{
                    showToast(listResponse.getMsg());
                }
            }
        });
    }

    private void successLoadGoods(List<GoodsDetailInfo> goodsDetailInfos) {
//        Logger.i("获取的商品列表",new Gson().toJson(goodsDetailInfos));
//        System.out.println(new Gson().toJson(goodsDetailInfos));
        if(adapter == null){
            adapter = new GoodsListAdapter(list_goods,getApplicationContext(),user.getShoppingcartId());
            rv.setAdapter(adapter);
        }
        list_goods.clear();
        list_goods.addAll(goodsDetailInfos);
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event){
        if(event.getCode() == EventCodes.OPEN_GOODSDETAIL_DIALOG){
            AddToCartDialog dialog = new AddToCartDialog();
            Bundle b = new Bundle();
            b.putString("goodsInfo",new Gson().toJson(list_goods.get((int)event.getData())));
            b.putInt("position" ,(int)event.getData());
            dialog.setArguments(b);
            dialog.show(getSupportFragmentManager());
        }else if(event.getCode() == EventCodes.SWITCH_COLLECT){
            if(list_goods.get((int)event.getData()).getIsCollect() == 0){
                collect(list_goods.get((int)event.getData()).getId());
            }else{
                unCollect(list_goods.get((int)event.getData()).getId());
            }
        }else if(event.getCode() == EventCodes.BUY_GOODS){
            buyGoods(event);
        }
    }

    private void buyGoods(Event event) {
        String[] strings = StringUtils.splitWithBlank((String) event.getData());
        Observable<ListResponse> observable = HttpRequest.getApiservice().goodsToOrder(Integer.valueOf(strings[0]), user.getCommomAddressId(), user.getId(), Integer.valueOf(strings[1]));
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showLongToast(listResponse.getMsg());
                }else{
                    showLongToast("数据异常");
                }
            }
        });

    }

    private void unCollect(Integer targetId) {
        Observable<ListResponse> observable = HttpRequest.getApiservice().unCollect(targetId,user.getId(),0);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                showLongToast(listResponse.getMsg());
            }
        });
    }

    private void collect(Integer targetId){
        Observable<ListResponse> observable = HttpRequest.getApiservice().collect(targetId,user.getId(),0);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                showLongToast(listResponse.getMsg());
            }
        });
    }
}
