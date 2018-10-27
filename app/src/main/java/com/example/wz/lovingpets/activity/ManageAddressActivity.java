package com.example.wz.lovingpets.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.AddressAdapter;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.entity.Address;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.UserUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//收货地址管理页面
@BindEventBus
public class ManageAddressActivity extends BaseActivity implements View.OnClickListener{

    private User user;
    private TextView title;
    private RecyclerView rv;
    private AddressAdapter adapter;
    private ImageView iv_add,iv_back;
    private List<Address> list_address = new ArrayList<>();
    private HttpRequest.ApiService api = HttpRequest.getApiservice();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        iv_add = findViewById(R.id.iv_titlebar_add);
        iv_back = findViewById(R.id.titlebar_iv_left);
        title = findViewById(R.id.titlebar_tv_title);
        rv = findViewById(R.id.manage_address_rv);
    }

    @Override
    protected void initData() {
        user = new UserUtil(this).getUser();
        title.setText("管理收货地址");
        adapter = new AddressAdapter(this,list_address);
        rv.setAdapter(adapter);
        getAddress();
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }
    private void getAddress(){
        Observable<ListResponse<Address>> observable = api.getAllAddress(user.getId());
        observable.subscribeOn(Schedulers.newThread())//它为指定任务启动一个新的线程。
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ListResponse<Address>>(){
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(ListResponse<Address> listResponse) {
                successLoadData(listResponse.getRows());
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        });
    }

    private void successLoadData(List<Address> rows) {
        if(adapter == null){
            adapter = new AddressAdapter(this,list_address);
            rv.setAdapter(adapter);
        }
        list_address.clear();
        list_address.addAll(rows);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left :
                finish();break;
            case R.id.iv_titlebar_add :
                intent2Activity(EditAddressActivity.class);break;
            default:
        }
    }

    @Subscribe(threadMode =  ThreadMode.MAIN)
    public void addressChanged(Event event){
        if(event.getCode() == EventCodes.SAVE_ADDRESS){
            getAddress();
        }
    }
}
