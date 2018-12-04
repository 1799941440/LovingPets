package com.example.wz.lovingpets.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.OrderCommentAdapter;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.entity.ServiceInfo;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.UserUtil;
import com.example.wz.lovingpets.widget.PriceView;
import com.google.gson.Gson;

import static com.example.wz.lovingpets.activity.MyApp.getContext;

public class OrderServerActivity extends BaseActivity {

    private PriceView pv;
    private Integer userId;
    private ImageView image;
    private RecyclerView rv;
    private ServiceInfo data;
    private LinearLayout ll_chooseTime;
    private OrderCommentAdapter adapter;
    private TextView tv_name, tv_shopName, tv_star, tv_address, tv_orderTimes,
            tv_payWay, tv_time, tv_describe, tv_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_server);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        pv = findViewById(R.id.order_server_pv);
        image = findViewById(R.id.order_server_image);
        rv = findViewById(R.id.order_server_rv);
        tv_name = findViewById(R.id.order_server_name);
        tv_shopName = findViewById(R.id.order_server_shopName);
        tv_star = findViewById(R.id.order_server_star);
        tv_address = findViewById(R.id.order_server_address);
        tv_orderTimes = findViewById(R.id.order_server_orderTimes);
        tv_payWay = findViewById(R.id.order_server_payWay);
        tv_time = findViewById(R.id.order_server_time);
        tv_describe = findViewById(R.id.order_server_describe);
        tv_order = findViewById(R.id.order_server_order);
        ll_chooseTime = findViewById(R.id.order_server_chooseTime);
    }

    @Override
    protected void initData() {
        userId = new UserUtil(getContext()).getUser().getId();
        data = new Gson().fromJson(getIntent().getStringExtra("data"), ServiceInfo.class);
        ImageUtil.loadNetImage(image, data.getImages());
        tv_name.setText(data.getServiceName());
        tv_shopName.setText(data.getShopName());
        tv_star.setText("评分:" + data.getServiceStar());
        tv_address.setText(data.getAddress());
        tv_orderTimes.setText("预定次数:"+data.getSales());
        tv_payWay.setText(data.getPayWay() == 0 ? "价格" : "预定价格");
        pv.setPrice(data.getPayWay() == 0 ? data.getPrice() : data.getOrderPrice());
        tv_describe.setText(data.getDescribe());
        tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adapter = new OrderCommentAdapter(getContext(), data.getCommentList());
        rv.setAdapter(adapter);
    }
}
