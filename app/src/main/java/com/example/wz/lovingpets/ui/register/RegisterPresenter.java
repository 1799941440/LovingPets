package com.example.wz.lovingpets.ui.register;

import android.content.Context;

import com.example.wz.lovingpets.base.IBasePresent;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.Md5Util;
import com.example.wz.lovingpets.utils.StringUtils;

import io.reactivex.Observable;

public class RegisterPresenter extends IBasePresent<RegisterActivity> {

    private final HttpRequest.ApiService api;

    public RegisterPresenter(RegisterContract.View view, HttpRequest.ApiService api) {
        this.api = api;
        attachMVPView(view);
    }

    public void register(String phone, String password) {
        StringBuilder sb = new StringBuilder("爱宠-");
        sb.append(StringUtils.genRandomNum(7));
        Observable<ListResponse<User>> observable = api.register(sb.toString(), Md5Util.md5(password),phone);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<User>>() {
            @Override
            public void onSuccess(ListResponse<User> listResponse) {
                if(mvpView.isActive){
                    mvpView.registerSuccess(listResponse.getRows().get(0),listResponse.isSuccess());
                }
            }
        });

    }
}
