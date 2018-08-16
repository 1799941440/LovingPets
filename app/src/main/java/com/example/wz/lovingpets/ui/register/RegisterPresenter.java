package com.example.wz.lovingpets.ui.register;

import android.content.Context;

import com.example.wz.lovingpets.net.HttpRequest;

public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View view;
    private final HttpRequest.ApiService api;

    public RegisterPresenter(RegisterContract.View view, HttpRequest.ApiService api) {
        this.view = view;
        this.api = api;
        view.setPresenter(this);
    }

    @Override
    public void register(String username, String password) {
        view.registerSuccess(null,true);
    }
}
