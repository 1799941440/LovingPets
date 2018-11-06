package com.example.wz.lovingpets.common;

import android.net.Uri;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ObservableDecorator {
    private SuccessCall successCall;
    public interface SuccessCall<T> {
        void onSuccess(T t);
    }
    public static <T> void decorate(Observable<T> observable, final SuccessCall successCall){
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(T t) {
                        successCall.onSuccess(t);
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
