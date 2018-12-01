package com.example.wz.lovingpets.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.GoodsDetailInfo;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.UserUtil;
import com.example.wz.lovingpets.widget.PriceView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GoodsList extends BaseActivity {

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
        Observable<ListResponse<GoodsDetailInfo>> observable = api.getGoods(classify,condition);
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
}
