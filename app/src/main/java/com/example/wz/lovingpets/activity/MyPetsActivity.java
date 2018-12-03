package com.example.wz.lovingpets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.PetAdapter;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.PetInfo;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.widget.ConfirmDialogBuilder;
import com.example.wz.lovingpets.widget.ConfirmDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

@BindEventBus
public class MyPetsActivity extends BaseFragmentActivity implements View.OnClickListener {

    private TextView title;
    private ImageView iv_add,iv_back;
    private RecyclerView rv;
    private PetAdapter adapter;
    private User user;
    private ConfirmDialog dialog;
    private List<PetInfo> list_pets = new ArrayList<>();
    private HttpRequest.ApiService api = HttpRequest.getApiservice();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);
        findViews();
        initData();
        getPets();
    }

    @Override
    protected void findViews() {
        title = findViewById(R.id.titlebar_tv_title);
        iv_add = findViewById(R.id.iv_titlebar_add);
        iv_back = findViewById(R.id.titlebar_iv_left);
        rv = findViewById(R.id.myPet_rv);
    }

    @Override
    protected void initData() {
        title.setText("我的宠物");
        iv_add.setVisibility(View.VISIBLE);
        user = MyApp.getInstance().getUser();
        adapter = new PetAdapter(list_pets,MyPetsActivity.this);
        rv.setAdapter(adapter);
        iv_add.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @SuppressLint("CheckResult")
    private void getPets(){
        Observable<ListResponse<PetInfo>> observable = api.getPets(user.getId());
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<PetInfo>>() {
            @Override
            public void onSuccess(ListResponse<PetInfo> listResponse) {
                successLoadPet(listResponse.getRows());
            }
        });
    }

    private void successLoadPet(List<PetInfo> rows) {
        if(adapter == null){
            adapter = new PetAdapter(list_pets,MyPetsActivity.this);
        }
        list_pets.clear();
        list_pets.addAll(rows);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_titlebar_add:
                intent2Activity(AddPetActivity.class);
            case R.id.titlebar_iv_left:
                finish();break;
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(Event<Integer> event){
        if(event.getCode() == EventCodes.DEL_PET){
            showDelDialog(event.getData());
        }else if(event.getCode() == EventCodes.MANAGE_PET){
            getPets();
        }
    }

    private void showDelDialog(final Integer data) {
        dialog= new ConfirmDialogBuilder()
                .setTv_title("删除宠物")
                .setTv_content("确认删除这个宠物吗？")
                .build();
        dialog.setCallback(new ConfirmDialog.Callback() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                delPet(data);
            }
        });
        dialog.show(getSupportFragmentManager());
    }

    private void delPet(Integer data) {
        Observable<ListResponse> observable = api.delete(data);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showToast("删除成功");
                }
            }
        });
    }
}
