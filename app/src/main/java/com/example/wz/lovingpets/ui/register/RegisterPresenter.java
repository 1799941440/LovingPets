package com.example.wz.lovingpets.ui.register;

import android.content.Context;

import com.example.wz.lovingpets.net.HttpRequest;

public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View view;
    private final HttpRequest.ApiService api;
    private Context context;

    public RegisterPresenter(RegisterContract.View view, HttpRequest.ApiService api, Context context) {
        this.view = view;
        this.api = api;
        this.context = context;
        view.setPresenter(this);
    }

    @Override
    public void register(String username, String password) {

    }
}
