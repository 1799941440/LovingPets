package com.example.wz.lovingpets.ui.login;

import android.content.Context;
import android.util.Log;

import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.StringUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private final HttpRequest.ApiService api;
    private Context context;
    public ListResponse<User> list;

    public LoginPresenter(LoginContract.View view, HttpRequest.ApiService api) {
        this.view = view;
        this.api = api;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void login(String username, String password) {
        if (StringUtils.isEmpty(username)) {
            view.showTip("用户名不能为空");
            return;
        }

        if (StringUtils.isEmpty(password)) {
            view.showTip("密码不能为空");
            return;
        }

        Observable<ListResponse<User>> observable = api.login(username, password);
        observable.subscribeOn(Schedulers.newThread())//它为指定任务启动一个新的线程。
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ListResponse<User>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ListResponse<User> listResponse) {
                if(!view.isActive()){
                    return;
                }
                if(listResponse.getRows().size()!=0){
                    Log.d("Tag", "登录onNext : 登录成功");
                    view.loginSuccess(listResponse.getRows().get(0),listResponse.isSuccess());
                }else {
                    Log.d("Tag", "登录onNext : 登录失败，没有该用户");
                    view.loginSuccess(null,listResponse.isSuccess());
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d("Tag", "登录 onComplete: ");
            }
        });
    }


}
