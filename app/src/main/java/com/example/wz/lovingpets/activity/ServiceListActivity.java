package com.example.wz.lovingpets.activity;

import android.opengl.ETC1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.ServiceAdapter;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.ServiceInfo;
import com.example.wz.lovingpets.net.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.example.wz.lovingpets.activity.MyApp.getContext;

public class ServiceListActivity extends BaseActivity {

    private int type;
    private RecyclerView rv;
    private ImageView iv_back;
    private TextView tv_search;
    private EditText et_condition;
    private ServiceAdapter adapter;
    private List<ServiceInfo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        findViews();
        initData();
        getServer("");
    }

    @Override
    protected void findViews() {
        rv = findViewById(R.id.service_list_rv);
        iv_back = findViewById(R.id.service_list_iv_back);
        tv_search = findViewById(R.id.tv_goods_search);
        et_condition = findViewById(R.id.et_goodsName);
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type",0);
        adapter = new ServiceAdapter(getContext(),list);
        rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rv.setAdapter(adapter);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getServer(et_condition.getText().toString().trim());
            }
        });
    }

    private void getServer(String condition) {
        Observable<ListResponse<ServiceInfo>> observable = HttpRequest.getApiservice().getServer(type,condition);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<ServiceInfo>>() {
            @Override
            public void onSuccess(ListResponse<ServiceInfo> listResponse) {
                list.clear();
                list.addAll(listResponse.getRows());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
