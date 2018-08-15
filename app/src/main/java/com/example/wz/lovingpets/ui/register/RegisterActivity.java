package com.example.wz.lovingpets.ui.register;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.entity.User;

public class RegisterActivity extends BaseActivity implements RegisterContract.View{

    private RegisterContract.Presenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void registerSuccess(User user, boolean success) {

    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

}
