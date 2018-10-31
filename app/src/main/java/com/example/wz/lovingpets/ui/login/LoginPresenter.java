package com.example.wz.lovingpets.ui.login;

import android.content.Context;
import android.util.Log;

import com.example.wz.lovingpets.base.IBasePresent;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.db.UserDao;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.utils.StringUtils;
import com.google.gson.Gson;

import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginPresenter extends IBasePresent<LoginContract.LoginView> {

    private final HttpRequest.ApiService api;
    public ListResponse<User> list;

    public LoginPresenter(LoginContract.LoginView view, HttpRequest.ApiService api) {
        this.api = api;
        attachMVPView(view);
    }

    public void login(String username, String password) {
        Logger.i("登录信息",username,password);
        Observable<ListResponse<User>> observable = api.login(username, password);
        observable.subscribeOn(Schedulers.newThread())//它为指定任务启动一个新的线程。
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ListResponse<User>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(ListResponse<User> listResponse) {
                if(!mvpView.isActive()){
                    return;
                }
                if(listResponse.getRows().size()!=0){
                    Log.d("Tag", "登录onNext : 登录成功");
                    EventBusUtils.sendEvent(new Event<User>(EventCodes.LOGIN_SUCCESS,listResponse.getRows().get(0)));
                    mvpView.loginSuccess(listResponse.getRows().get(0),listResponse.isSuccess());
                }else {
                    Log.d("Tag", "登录onNext : 登录失败，没有该用户");
                    mvpView.loginSuccess(null,listResponse.isSuccess());
                }
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
            @Override
            public void onComplete() {

            }
        });
    }


}
