package com.example.wz.lovingpets.base;

public class IBasePresent<P extends BaseContract.BaseView> implements BaseContract.BasePresenter<BaseContract.BaseView> {
    public P mvpView;

    @Override
    public void attachMVPView(BaseContract.BaseView view) {
        this.mvpView = (P) view;
        mvpView.setPresenter(this);//给P的子类赋值
    }

    @Override
    public void detachMVPView() {
        if (mvpView != null){
            mvpView = null;
        }
    }
}