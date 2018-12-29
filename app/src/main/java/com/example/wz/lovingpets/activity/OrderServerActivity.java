package com.example.wz.lovingpets.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.OrderCommentAdapter;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.ServiceInfo;
import com.example.wz.lovingpets.entity.ServiceOrder;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.UserUtil;
import com.example.wz.lovingpets.widget.PriceView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observable;

import static com.example.wz.lovingpets.activity.MyApp.getContext;

public class OrderServerActivity extends BaseActivity {

    private Calendar c;
    private PriceView pv;
    private Integer userId;
    private ImageView image;
    private RecyclerView rv;
    private ServiceInfo data;
    private EditText et_orderMsg;
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
        et_orderMsg = findViewById(R.id.order_server_orderMsg);
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
        c = Calendar.getInstance();
        tv_time.setText(c.get(Calendar.YEAR)+"年"+c.get(Calendar.MONTH)+"月"+c.get(Calendar.DAY_OF_MONTH)+"日");
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
                orderServer();
            }
        });
        ll_chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                DatePickerDialog dp = new DatePickerDialog(OrderServerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_time.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
                        c.set(year,month,dayOfMonth);
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });
        adapter = new OrderCommentAdapter(getContext(), data.getCommentList());
        rv.setAdapter(adapter);
    }

    private void orderServer() {
        ServiceOrder so = new ServiceOrder();
        so.setServiceId(data.getId());
        so.setUserId(userId);
        so.setOrderDate(new Date());
        so.setServerDate(c.getTime());
        so.setPayWay(data.getPayWay());
        if(data.getPayWay() == 0){
            so.setFinalPrice(data.getPrice());
        }else{
            so.setOrderPrice(data.getOrderPrice());
        }
        so.setOrderMessage(et_orderMsg.getText().toString().trim());
        so.setShopMessage("");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Observable<ListResponse> observable = HttpRequest.getApiservice().addServiceOrder(
                gson.toJson(so)
        );
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showLongToast("预约服务成功");
                    finish();
                }
            }
        });
    }
}
