package com.example.wz.lovingpets.ui.register;

import android.content.Context;

import com.example.wz.lovingpets.base.IBasePresent;
import com.example.wz.lovingpets.net.HttpRequest;

public class RegisterPresenter extends IBasePresent<RegisterActivity> {

    private final HttpRequest.ApiService api;

    public RegisterPresenter(RegisterContract.View view, HttpRequest.ApiService api) {
        this.api = api;
        attachMVPView(view);
    }

    public void register(String username, String password) {
        mvpView.registerSuccess(null,true);
    }
}
